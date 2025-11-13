# Multi-stage build for Maven projects
FROM maven:3.8.3-openjdk-21 AS build
WORKDIR /app

# Copy Maven wrapper and pom.xml first for better caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x ./mvnw

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN ./mvnw dependency:go-offline -B

# Copy source code and build
COPY src src
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Create non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy the jar file
COPY --from=build /app/target/*.jar app.jar

# Use environment variable for port (Render sets this automatically)
EXPOSE ${PORT:-8080}

# Use exec form for better signal handling
ENTRYPOINT ["java", "-jar", "app.jar"]