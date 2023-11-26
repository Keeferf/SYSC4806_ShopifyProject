# Use the official OpenJDK image for JDK 17 as the base image
FROM openjdk:17-jdk-slim


# Set the working directory inside the container to /app
WORKDIR /app


# Copy the JAR file from your target directory to the container
COPY target/SYSC4806_ShopifyProject-1.0-SNAPSHOT.jar ./app.jar


# Inform Docker that the container listens on port 8080 at runtime
EXPOSE 8080


# Command to execute the application
CMD ["java", "-jar", "app.jar"]
