FROM eclipse-temurin:17-jdk-alpine

LABEL authors="kamil"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 7070
ENTRYPOINT ["java","-jar","/app.jar"]