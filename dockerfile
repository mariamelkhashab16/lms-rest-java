# Use an OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/library-rest-0.0.1-SNAPSHOT.jar app.jar
COPY .env ./.env

# Expose the application's port (change 8080 to your app's port if different)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
