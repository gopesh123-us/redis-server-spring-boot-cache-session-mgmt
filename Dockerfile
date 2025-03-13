FROM openjdk:21
WORKDIR /app
COPY target/redis-sb-app.jar app.jar
EXPOSE 8083:8083
ENTRYPOINT ["java", "-jar", "app.jar"]