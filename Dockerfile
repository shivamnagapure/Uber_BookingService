# Stage 1: Build
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app
COPY . .

# Accept GitHub credentials as build args
ARG GITHUB_USERNAME
ARG GITHUB_TOKEN

# Export them as environment variables for Gradle
ENV GITHUB_USERNAME=$GITHUB_USERNAME
ENV GITHUB_TOKEN=$GITHUB_TOKEN

RUN chmod +x gradlew && ./gradlew clean build -x test

# Stage 2: Runtime
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
