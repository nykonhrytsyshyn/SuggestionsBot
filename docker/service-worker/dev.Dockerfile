FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN apk add --no-cache curl

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/app/SuggestService-worker-latest.jar"]
