FROM eclipse-temurin:21-jre-alpine AS service-worker

WORKDIR /app

RUN apk add --no-cache curl

ENTRYPOINT exec sh -c "java -jar -Dspring.profiles.active=dev /app/${WORKER_JAR_OUTPUT}"
