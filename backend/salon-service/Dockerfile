# Use a lightweight base image with JDK 17
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the host to the container
COPY target/salon-service-0.0.1-SNAPSHOT.jar /app/salon-service.jar

# Run the application
CMD ["java", "-jar", "/app/salon-service.jar"]
