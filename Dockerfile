FROM openjdk:21
WORKDIR /app
COPY build/libs/BoosterPlatform-1.0-SNAPSHOT.jar app.jar
COPY . .
EXPOSE 6969
ENTRYPOINT ["java", "-jar", "app.jar"]
