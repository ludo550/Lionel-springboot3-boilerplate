FROM eclipse-temurin:22.0.2_9-jre-ubi9-minimal
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]