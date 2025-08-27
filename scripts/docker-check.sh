#!/usr/bin/env bash

set -e

start_docker_linux() {
    if command -v systemctl >/dev/null 2>&1; then
        echo "🔧 Starting Docker with systemctl..."
        sudo systemctl start docker
    elif command -v service >/dev/null 2>&1; then
        echo "🔧 Starting Docker with service..."
        sudo service docker start
    else
        echo "❌ Could not find a way to start Docker on Linux."
        echo "❌ Please start it manually."
        echo "👉 If Docker is not installed, please install it from $DOCKER_INSTALL_URL"
        exit 1
    fi
}

start_docker_macos() {
    echo "🍏 Opening Docker Desktop..."
    open -a Docker || {
        echo "❌ Could not open Docker Desktop."
        echo "❌ Please start it manually."
        echo "👉 If Docker is not installed, please install it from $DOCKER_INSTALL_URL"
        exit 1
    }
}

start_docker_windows() {
    if [ -f "$WINDOWS_DOCKER_PATH" ]; then
        echo "🍔 Starting Docker Desktop on Windows..."
        "$WINDOWS_DOCKER_PATH" &
    else
        echo "❌ Docker Desktop not found at '$WINDOWS_DOCKER_PATH'."
        echo "❌ Please start it manually."
        echo "👉 If Docker is not installed, please install it from $DOCKER_INSTALL_URL"
        exit 1
    fi
}

check_docker() {
    if ! docker info >/dev/null 2>&1; then
        echo "🐳 Docker is not running. Attempting to start it..."

        case "$(uname -s)" in
            Linux*) start_docker_linux ;;
            Darwin*) start_docker_macos ;;
            CYGWIN*|MINGW*|MSYS*) start_docker_windows ;;
            *)
                echo "❓ Unknown OS: $(uname -s). Please start Docker manually."
                exit 1
                ;;
        esac

        echo "⏳ Waiting for Docker to start..."
        while ! docker info >/dev/null 2>&1; do
            sleep 2
        done
        echo "✅ Docker started successfully!"
    else
        echo "🐳 Docker is already running."
    fi
}

check_docker_compose() {
    if command -v docker-compose &> /dev/null; then
        DOCKER_COMPOSE_CMD="docker-compose"
    elif command -v docker &> /dev/null && docker compose version &> /dev/null; then
        DOCKER_COMPOSE_CMD="docker compose"
    else
        echo "⛔ ❌ Docker Compose is not installed or not in your PATH."
        echo "👉 Please install it from $DOCKER_INSTALL_URL"
        exit 1
    fi
    export DOCKER_COMPOSE_CMD
    echo "✅ Docker Compose found: $DOCKER_COMPOSE_CMD"
}

source ./load-env.sh || exit 1
check_docker
check_docker_compose
