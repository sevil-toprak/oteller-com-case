# -------------------------------
# STAGE 1: Build (Java 21 JDK)
# -------------------------------
FROM eclipse-temurin:21-jdk AS build
LABEL authors="seviltoprak"

WORKDIR /app

# Copy all project files (including gradlew) into the container
COPY . .
RUN chmod +x gradlew
RUN ./gradlew clean bootJar --no-daemon

# -------------------------------
# STAGE 2: Runtime (Java 21 JRE)
# -------------------------------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the JAR built in the first stage
COPY --from=build /app/build/libs/*.jar app.jar

# Export port
EXPOSE 8081

# start application
ENTRYPOINT ["java", "-jar", "app.jar"]
