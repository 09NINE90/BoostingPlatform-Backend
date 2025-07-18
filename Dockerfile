ARG JAVA_VERSION=21
FROM openjdk:${JAVA_VERSION}-slim
COPY build/libs/*.jar app.jar
ENV SERVER_PORT=6969 \
    JAVA_OPTS=""
EXPOSE ${SERVER_PORT}
ENTRYPOINT ["java", "-jar", "app.jar"]