FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
ENTRYPOINT ["java", "-jar", "/app/SuggestService-bot-latest.jar"]
