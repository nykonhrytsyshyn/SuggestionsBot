FROM eclipse-temurin:21-jre-alpine AS service-bot

WORKDIR /app

RUN apk add --no-cache curl

ENTRYPOINT exec sh -c "java -jar -Dspring.profiles.active=dev /app/${BOT_JAR_OUTPUT}"
