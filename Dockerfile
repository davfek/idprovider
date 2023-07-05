FROM openjdk:17-alpine
COPY /Docker/demo-0.0.1-SNAPSHOT.jar /demo.jar
COPY ./Import.txt /app/import.txt
EXPOSE 8080
CMD ["java", "-jar", "demo.jar"]