FROM openjdk:21
WORKDIR /app
COPY build/libs/BoosterPlatform-1.0-SNAPSHOT.jar app.jar
COPY . .
EXPOSE 6969
ENV PASSWORD_DB @P05tGreSQL!
ENV USERNAME_DB postgres
ENV JWT_SECRET_KEY ajscqSVPNj4GNzF+Ln2H6yaE2etWGExa618+TDP96ZE=
ENV URL_DB jdbc:postgresql://db:5432/boosting_platform
ENTRYPOINT ["java", "-jar", "app.jar"]