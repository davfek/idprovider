FROM maven:3.5-jdk-11 AS build
COPY target/demo-0.0.1-SNAPSHOT.jar /demo.jar
EXPOSE 8080
CMD ["java", "-jar", "/demo.jar"]