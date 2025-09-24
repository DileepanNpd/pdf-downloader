# Use OpenJDK 17 Alpine for lightweight image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the JAR built by Maven
COPY target/pdfdownloader-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 (Cloud Run default)
EXPOSE 8080

# Run the Spring Boot JAR
ENTRYPOINT ["java","-jar","app.jar"]
