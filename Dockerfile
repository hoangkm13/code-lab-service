# Build stage
FROM maven:3.6.0 AS BUILD_STAGE
WORKDIR /app
COPY . .
RUN ["mvn", "clean", "install", "-Dmaven.test.skip=true"]

# Run stage
FROM openjdk:11.0.6-jre-slim
WORKDIR /app

USER root

COPY --from=BUILD_STAGE /app/target/*.jar ../app.jar

EXPOSE 8004