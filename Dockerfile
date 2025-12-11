FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/devicemanager-0.0.1-SNAPSHOT.jar devicemanager.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "devicemanager.jar"]
