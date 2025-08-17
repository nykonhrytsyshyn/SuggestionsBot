#!/usr/bin/env bash
set -e

COMMAND="$1"
shift || true
DC_FLAGS=("$@")

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

usage() {
    echo -e "${GREEN}Usage:${NC} $0 {build|up|all|down|clean} [docker-compose flags]"
    echo
    echo -e "${YELLOW}Commands:${NC}"
    echo -e "  build      🔨 Build the Docker images"
    echo -e "  up         🚀 Start the containers"
    echo -e "  all        🛠️ Build and start the containers"
    echo -e "  down       🛑 Stop and remove the containers"
    echo -e "  clean      🧹 Stop containers and remove containers, volumes, and images"
    echo
    echo -e "${YELLOW}Examples:${NC}"
    echo "  $0 up -d                     Run containers in detached mode"
    echo "  $0 all --force-recreate      Rebuild and recreate all containers"
    echo "  $0 down                      Stop and remove all containers"
    echo "  $0 clean                     Completely clean everything"
    echo
    echo -e "${BLUE}Tips:${NC}"
    echo "  - Pass extra docker-compose flags after the command, e.g.: $0 up --build"
    exit 1
}

[[ -z "$COMMAND" ]] && usage
[[ "$COMMAND" != "build" && "$COMMAND" != "up" && "$COMMAND" != "all" && "$COMMAND" != "down" && "$COMMAND" != "clean" ]] && {
    echo -e "${RED}Error:${NC} Unknown command '$COMMAND'"
    usage
}

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
    clean)
        echo "🧹 Cleaning everything: containers, volumes, and images..."
        docker compose -f ../docker/docker-compose.dev.yml -f ../docker/docker-compose.dev.build.yml down -v --rmi all --remove-orphans
        ;;
esac
