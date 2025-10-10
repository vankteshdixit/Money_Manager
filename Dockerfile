# ---------- Stage 1: Build ----------
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies (cached layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build the JAR
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Stage 2: Runtime ----------
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=build /app/target/Money_Manager-0.0.1-SNAPSHOT.jar moneymanager-v1.0.jar

# Set the Spring profile
ENV SPRING_PROFILES_ACTIVE=prod

# Expose port 9090 (or 8080 if your app uses that)
EXPOSE 9090

# Run the application
ENTRYPOINT ["java", "-jar", "moneymanager-v1.0.jar"]
