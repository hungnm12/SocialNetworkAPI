#FROM ubuntu:latest
#LABEL authors="dell"
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
#COPY /home/dell/target/Social-Network-API-0.0.1-SNAPSHOT.jar Social-Network-API.jar
EXPOSE 8000
COPY target/Social-Network-API-0.0.1-SNAPSHOT.jar Social-Network-API.jar
ENTRYPOINT ["java","-jar","/Social-Network-API.jar"]
#ENTRYPOINT ["java", "-jar","/Social-Network-API.jar"]
#<artifactId>demo</artifactId>
 #	<version>0.0.1-SNAPSHOT</version>
# Social-Network-API-0.0.1-SNAPSHOT.jar
