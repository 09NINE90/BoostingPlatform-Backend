FROM openjdk:21
WORKDIR /app
COPY build/libs/BoosterPlatform-1.0-SNAPSHOT.jar app.jar
COPY . .
EXPOSE 6969
ENV PASSWORD_DB password
ENV USERNAME_DB username
ENV JWT_SECRET_KEY secret_key
ENV URL_DB jdbc:postgresql://db:5432/db_name
ENTRYPOINT ["java", "-jar", "app.jar"]
