FROM maven:3.8.3-openjdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml -B package -DskipTests

FROM adoptopenjdk:11-jre-hotspot
COPY --from=build "/home/app/target/code-lab-svc-0.0.1-SNAPSHOT.jar" "/opt/code-lab-svc.jar"

EXPOSE 8004

ENTRYPOINT ["java", "-jar", "/opt/code-lab-svc.jar"]

