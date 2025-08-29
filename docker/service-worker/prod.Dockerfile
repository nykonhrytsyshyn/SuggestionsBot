# Stage 1: Builder
FROM gradle:9-jdk21 AS builder

WORKDIR /repo
COPY app /repo/app

RUN cd app && ./gradlew clean build --no-daemon -x test

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine AS service-worker

WORKDIR /app
COPY --from=builder /repo/app/build/service/*worker*.jar /app/worker.jar

RUN apk add --no-cache curl

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/app/worker.jar"]
