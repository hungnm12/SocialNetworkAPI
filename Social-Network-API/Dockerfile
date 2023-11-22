FROM ubuntu:latest
LABEL authors="dell"
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]
#ENTRYPOINT ["java", "-jar","/Social-Network-API.jar"]
#<artifactId>demo</artifactId>
 #	<version>0.0.1-SNAPSHOT</version>
# Social-Network-API-0.0.1-SNAPSHOT.jar
