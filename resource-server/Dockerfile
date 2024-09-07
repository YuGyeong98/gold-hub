FROM gradle:8.10.0-jdk17 AS resource-build
WORKDIR /app
COPY ./ /app/resource-server
WORKDIR /app/resource-server
RUN ./gradlew build -x test --no-daemon

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=resource-build app/resource-server/build/libs/*.jar resource-server.jar
EXPOSE 9999 50052
ENTRYPOINT ["java", "-jar", "resource-server.jar"]