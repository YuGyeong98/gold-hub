FROM gradle:8.10.0-jdk17 AS auth-build
WORKDIR /app
COPY ./ /app/auth-server
WORKDIR /app/auth-server
RUN ./gradlew build -x test --no-daemon

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=auth-build app/auth-server/build/libs/*.jar auth-server.jar
EXPOSE 8888 50051
ENTRYPOINT ["java", "-jar", "auth-server.jar"]