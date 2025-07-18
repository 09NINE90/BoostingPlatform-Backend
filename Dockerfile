ARG JAVA_VERSION=21
FROM openjdk:${JAVA_VERSION}-slim as builder

WORKDIR /app
COPY build/libs/*.jar application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM openjdk:${JAVA_VERSION}-slim
WORKDIR /app

COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/application/ ./

ENV SERVER_PORT=6969 \
    JAVA_OPTS=""

EXPOSE ${SERVER_PORT}

ENTRYPOINT exec java ${JAVA_OPTS} org.springframework.boot.loader.JarLauncher