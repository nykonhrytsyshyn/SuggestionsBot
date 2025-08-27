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
COMPOSE_DEV="../docker/docker-compose.dev.yml"
COMPOSE_EXPOSE="../docker/docker-compose.dev.expose.yml"
COMPOSE_BUILD="../docker/docker-compose.dev.build.yml"

# Check for --expose-ports flag
USE_EXPOSE=false
for i in "${!DC_FLAGS[@]}"; do
    if [[ "${DC_FLAGS[$i]}" == "--expose-ports" ]]; then
        USE_EXPOSE=true
        unset 'DC_FLAGS[$i]'
        break
    fi
done

usage() {
    echo -e "${GREEN}Usage:${NC} $0 {build|up|all|down|clean} [docker-compose flags] [--expose-ports]"
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
    echo "  $0 up --expose-ports         Start containers with exposed ports"
    echo
    echo -e "${BLUE}Tips:${NC}"
    echo "  - Pass extra docker-compose flags after the command, e.g.: $0 up --build"
    exit 1
}

[[ -z "$COMMAND" ]] && usage
[[ "$COMMAND" != "build" && "$COMMAND" != "up" && "$COMMAND" != "all" && "$COMMAND" != "down" && "$COMMAND" != "clean" ]] && {
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
if [[ "$COMMAND" == "up" ]]; then
    COMPOSE_FILES=( "$COMPOSE_DEV" )
    $USE_EXPOSE && COMPOSE_FILES+=( "$COMPOSE_EXPOSE" )
else
    COMPOSE_FILES=( "$COMPOSE_DEV" "$COMPOSE_BUILD" )
    $USE_EXPOSE && COMPOSE_FILES+=( "$COMPOSE_EXPOSE" )
fi

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
        echo "🔨 Building..."
        $DOCKER_COMPOSE_CMD "${COMPOSE_ARGS[@]}" up --build "${DC_FLAGS[@]}" builder --remove-orphans
        ;;
    up)
        echo "🚀 Starting containers..."
        $DOCKER_COMPOSE_CMD "${COMPOSE_ARGS[@]}" up "${DC_FLAGS[@]}" --remove-orphans
        ;;
    all)
        echo "🛠️ Building and starting containers..."
        $DOCKER_COMPOSE_CMD "${COMPOSE_ARGS[@]}" up --build "${DC_FLAGS[@]}" --remove-orphans
        ;;
    down)
        echo "🛑 Stopping and removing containers..."
        $DOCKER_COMPOSE_CMD "${COMPOSE_ARGS[@]}" down "${DC_FLAGS[@]}"
        ;;
    clean)
        echo "🧹 Cleaning everything: containers, volumes, and images..."
        $DOCKER_COMPOSE_CMD "${COMPOSE_ARGS[@]}" down -v --rmi all --remove-orphans
        ;;
esac
