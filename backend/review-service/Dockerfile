# Use an official OpenJDK 17 runtime as a parent image
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built jar file into the container (you can change the name)
COPY target/review-service-0.0.1-SNAPSHOT.jar /app/review-service.jar

# Run the jar file
CMD ["java", "-jar", "/app/review-service.jar"]
