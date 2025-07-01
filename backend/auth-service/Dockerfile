FROM openjdk:21-jdk-slim

WORKDIR /app

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]