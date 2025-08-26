FROM gradle:9.0.0-jdk21 AS builder

WORKDIR /repo

COPY app /repo/app

CMD ["sh", "-c", "\
  cd app && ./gradlew clean build --no-daemon && \
  mkdir -p ${OUTPUT_DIR} && \
  cp ${BUILD_DIR}${BOT_JAR_INPUT} ${OUTPUT_DIR}${BOT_JAR_OUTPUT} && \
  cp ${BUILD_DIR}${WORKER_JAR_INPUT} ${OUTPUT_DIR}${WORKER_JAR_OUTPUT} \
"]
