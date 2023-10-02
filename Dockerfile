FROM openjdk:19-slim
LABEL authors="ivan"

WORKDIR /app
COPY build/libs/Tasks-0.0.1-SNAPSHOT.jar /app/tasks.jar
EXPOSE 8080

CMD ["java", "-jar", "/app/tasks.jar"]