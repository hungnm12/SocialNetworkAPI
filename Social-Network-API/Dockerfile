FROM ubuntu:latest
LABEL authors="dell"
FROM maven:3.8.4-openjdk-17 as build
COPY .. .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.2-jdk-slim
COPY --from=build /target/0.0.1-SNAPSHOT-Social-Network-API.jar Social-Network-API.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar","Social-Network-API.jar"]
#<artifactId>demo</artifactId>
 #	<version>0.0.1-SNAPSHOT</version>