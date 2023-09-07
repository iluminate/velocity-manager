FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar ./app.jar

EXPOSE 5500

CMD ["java", "-jar", "app.jar"]