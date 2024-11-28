FROM openjdk:21-slim
ARG JAR_FILE=target/*.jar
COPY target/client-0.0.1-SNAPSHOT.jar client.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "client.jar"]