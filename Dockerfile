FROM openjdk:18-jdk-alpine
VOLUME /tmp
EXPOSE 5432
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
