FROM alpine:3.21 AS builder
RUN apk add --no-cache gettext
COPY ./docker/prometheus.yml /tmp/prometheus.yml.template

ARG PROMETHEUS_PORT
ARG BOT_PORT
ARG WORKER_PORT
ARG RABBITMQ_WEB_PORT
ARG RABBITMQ_PORT
ARG POSTGRES_PORT
ARG REDIS_PORT

ENV PROMETHEUS_PORT=${PROMETHEUS_PORT}
ENV BOT_PORT=${BOT_PORT}
ENV WORKER_PORT=${WORKER_PORT}
ENV RABBITMQ_WEB_PORT=${RABBITMQ_WEB_PORT}
ENV RABBITMQ_PORT=${RABBITMQ_PORT}
ENV POSTGRES_PORT=${POSTGRES_PORT}
ENV REDIS_PORT=${REDIS_PORT}

RUN envsubst < /tmp/prometheus.yml.template > /tmp/prometheus.yml

FROM prom/prometheus:latest AS prometheus
COPY --from=builder /tmp/prometheus.yml /etc/prometheus/prometheus.yml
ENTRYPOINT ["/bin/prometheus", "--config.file=/etc/prometheus/prometheus.yml"]
