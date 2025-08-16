#!/usr/bin/env bash
set -e

COMMAND="$1"
shift || true
DC_FLAGS=("$@")

usage() {
  echo "Usage: $0 {build|up|all|down} [docker-compose flags]"
  echo "Examples:"
  echo "  $0 up -d                      Run containers in detached mode"
  echo "  $0 all --force-recreate       Rebuild and recreate all containers"
  echo "  $0 down                       Stop and remove all containers"
  exit 1
}

[[ -z "$COMMAND" ]] && usage
[[ "$COMMAND" != "build" && "$COMMAND" != "up" && "$COMMAND" != "all" && "$COMMAND" != "down" ]] && usage

source ./load-env.sh || exit 1
source ./docker-check.sh || exit 1

case $COMMAND in
  build)
    echo "🔨 Building..."
    docker compose -f ../docker/docker-compose.dev.yml -f ../docker/docker-compose.dev.build.yml up --build "${DC_FLAGS[@]}" builder --remove-orphans
    ;;
  up)
    echo "🚀 Starting containers..."
    docker compose -f ../docker/docker-compose.dev.yml up "${DC_FLAGS[@]}" --remove-orphans
    ;;
  all)
    echo "🛠️ Building and starting containers..."
    docker compose -f ../docker/docker-compose.dev.yml -f ../docker/docker-compose.dev.build.yml up --build "${DC_FLAGS[@]}" --remove-orphans
    ;;
  down)
    echo "🛑 Stopping and removing containers..."
    docker compose -f ../docker/docker-compose.dev.yml -f ../docker/docker-compose.dev.build.yml down "${DC_FLAGS[@]}"
    ;;
esac
