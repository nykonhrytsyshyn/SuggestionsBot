#!/usr/bin/env bash
set -e

# Command parsing
COMMAND="$1"
shift || true
DC_FLAGS=("$@")

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

# Docker Compose file paths
COMPOSE="../docker/docker-compose.yml"
COMPOSE_PROD="../docker/docker-compose.prod.yml"
COMPOSE_BUILD="../docker/docker-compose.prod.build.yml"

# Docker Stack file and name for Swarm
STACK_NAME="suggestionsbot"
STACK_FILE="../docker/docker-stack.prod.swarm.yml"

# Check for commands that require build
USE_BUILD=true
[[ "$COMMAND" == "up" ]] && USE_BUILD=false

usage() {
    echo -e "${GREEN}Usage:${NC} $0 {build|push|release|up|down|clean} [docker-compose flags]"
    echo
    echo -e "${YELLOW}Commands:${NC}"
    echo -e "  build      🔨 Build the production Docker images"
    echo -e "  push       📤 Push the built images to Docker Hub"
    echo -e "  release    🚀 Build and push images in one step"
    echo -e "  up         🚀 Start the containers from already built images"
    echo -e "  swarm      🌐 Deploy services using Docker Swarm"
    echo -e "  swarm-down 🛑 Remove services from Docker Swarm"
    echo -e "  down       🛑 Stop and remove the containers"
    echo -e "  clean      🧹 Remove containers, volumes, and images"
    echo
    echo -e "${YELLOW}Examples:${NC}"
    echo "  $0 build                     Build production images"
    echo "  $0 push                      Push images to Docker Hub"
    echo "  $0 release                   Build and push images in one step"
    echo "  $0 up -d                     Start containers in detached mode"
    echo "  $0 swarm                     Deploy services using Docker Swarm"
    echo "  $0 down                      Stop containers"
    echo "  $0 clean                     Remove everything"
    echo
    echo -e "${BLUE}Tips:${NC}"
    echo "  - Pass extra docker-compose flags after the command, e.g.: $0 up --build"
    exit 1
}

[[ -z "$COMMAND" ]] && usage
[[ "$COMMAND" != "build" && "$COMMAND" != "push" && "$COMMAND" != "release" && "$COMMAND" != "up" && "$COMMAND" != "down" && "$COMMAND" != "clean" && "$COMMAND" != "swarm" && "$COMMAND" != "swarm-down" ]] && {
    echo -e "⛔ ${RED}ERROR:${NC} Unknown command '$COMMAND'"
    usage
}

# Load environment and check Docker
source ./load-env.sh || exit 1
source ./docker-check.sh || exit 1

# OS detection
CYGWIN=false
MSYS=false
DARWIN=false

OS_TYPE=$(uname)
case "$OS_TYPE" in
  CYGWIN* ) CYGWIN=true ;;
  MSYS* | MINGW* ) MSYS=true ;;
  Darwin* ) DARWIN=true ;;
esac

# Select compose files depending on the command
COMPOSE_FILES=( "$COMPOSE" "$COMPOSE_PROD" )
$USE_BUILD && COMPOSE_FILES+=( "$COMPOSE_BUILD" )

# Convert paths for Cygwin/MSYS/Darwin
if $CYGWIN || $MSYS || $DARWIN; then
    for i in "${!COMPOSE_FILES[@]}"; do
        if $CYGWIN || $MSYS; then
            COMPOSE_FILES[i]=$(cygpath -m "${COMPOSE_FILES[i]}")
        elif $DARWIN; then
            COMPOSE_FILES[i]=$(realpath "${COMPOSE_FILES[i]}")
        fi
    done
fi

# Build compose args with -f for each file
COMPOSE_ARGS=()
for f in "${COMPOSE_FILES[@]}"; do
    COMPOSE_ARGS+=("-f" "$f")
done

# Execute command
case $COMMAND in
    build)
        echo "🔨 Building production images..."
        $DOCKER_COMPOSE_CMD "${COMPOSE_ARGS[@]}" build "${DC_FLAGS[@]}"
        ;;
    push)
        echo "📤 Pushing production images to Docker Hub..."
        $DOCKER_COMPOSE_CMD "${COMPOSE_ARGS[@]}" push "${DC_FLAGS[@]}"
        ;;
    release)
        echo "🚀 Release: Build and push images..."
        $DOCKER_COMPOSE_CMD "${COMPOSE_ARGS[@]}" build "${DC_FLAGS[@]}"
        $DOCKER_COMPOSE_CMD "${COMPOSE_ARGS[@]}" push "${DC_FLAGS[@]}"
        ;;
    up)
        echo "🚀 Starting production containers..."
        $DOCKER_COMPOSE_CMD "${COMPOSE_ARGS[@]}" up "${DC_FLAGS[@]}" --remove-orphans
        ;;
    down)
        echo "🛑 Stopping and removing containers..."
        $DOCKER_COMPOSE_CMD "${COMPOSE_ARGS[@]}" down "${DC_FLAGS[@]}" --remove-orphans
        ;;
    clean)
        echo "🧹 Cleaning everything: containers, volumes, and images..."
        $DOCKER_COMPOSE_CMD "${COMPOSE_ARGS[@]}" down -v --rmi all
        ;;
    swarm)
        echo "🌐 Deploying stack to Docker Swarm..."
        docker stack deploy -c "$STACK_FILE" "$STACK_NAME" --detach=false
        ;;
    swarm-down)
        echo "🛑 Removing stack from Docker Swarm..."
        docker stack rm "$STACK_NAME"
        echo "⌛ Waiting for services to stop..."
        while docker service ls --filter label=com.docker.stack.namespace="$STACK_NAME" --format '{{.ID}}' | grep -q .; do
          sleep 2
        done
        echo "✅ Stack '$STACK_NAME' removed successfully."
        ;;
    *)
        echo -e "⛔ ${RED}ERROR:${NC} Unknown command '$COMMAND'"
        usage
        ;;
esac
