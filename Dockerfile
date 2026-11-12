FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn -B -ntp package

FROM eclipse-temurin:25-jre

WORKDIR /app
RUN mkdir -p /app/data && chown 10001:10001 /app/data
COPY --from=build --chown=10001:10001 /workspace/target/*.war /app/app.war

USER 10001:10001
EXPOSE 3000
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "/app/app.war"]
