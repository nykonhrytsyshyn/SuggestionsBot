FROM gradle:9.0.0-jdk21
WORKDIR /repo
COPY app /repo/app

CMD ["sh", "-c", "\
  cd app && ./gradlew clean build --no-daemon && \
  mkdir -p /output && \
  cp build/service/*bot*.jar /output/SuggestService-bot-latest.jar && \
  cp build/service/*worker*.jar /output/SuggestService-worker-latest.jar \
"]
