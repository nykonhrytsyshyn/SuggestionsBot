FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
ENTRYPOINT ["java", "-jar", "/app/SuggestService-worker-latest.jar"]
