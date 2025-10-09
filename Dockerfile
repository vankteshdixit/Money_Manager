FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/Money_Manager-0.0.1-SNAPSHOT.jar moneymanager-v1.0.jar
EXPOSE 9090
ENTRYPOINT ["java", "jar", "moneymanager-v1.0.jar"]

