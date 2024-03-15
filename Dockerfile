FROM openjdk:17
ADD target/orchestrator-1.0.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]