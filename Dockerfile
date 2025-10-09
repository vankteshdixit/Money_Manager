# Use OpenJDK 17 slim runtime
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the jar file
COPY target/Money_Manager-0.0.1-SNAPSHOT.jar moneymanager-v1.0.jar

# Expose port 9090
EXPOSE 9090

# Start the application
ENTRYPOINT ["java","-jar","moneymanager-v1.0.jar"]
