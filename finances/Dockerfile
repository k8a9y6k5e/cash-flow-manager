FROM eclipse-temurin:17
LABEL authors="kayke"

WORKDIR /src/

COPY target/finances-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
