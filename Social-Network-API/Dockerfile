# Use the Maven image as a build stage
FROM maven:3.8.4-openjdk-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Use the OpenJDK image as the final base image
FROM openjdk:17.0.2-jdk-slim
WORKDIR /app
COPY --from=build /app/Social-Network-API/target/0.0.1-SNAPSHOT-Social-Network-API.jar Social-Network-API.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "Social-Network-API.jar"]