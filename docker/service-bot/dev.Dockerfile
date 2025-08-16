FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/app/SuggestService-bot-latest.jar"]
