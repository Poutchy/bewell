# Use an official OpenJDK 17 runtime as a parent image
FROM openjdk:25-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built jar file into the container (you can change the name)
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
