#!/usr/bin/env bash

set -e

[[ -n "$ENV_LOADED" ]] && return
export ENV_LOADED=1

if [ -f "./.env" ]; then
    echo "📄 Loading environment from '.env'"
    source ./.env
elif [ -f "./.env.example" ]; then
    echo "📄 Loading environment from '.env.example'"
    echo "📄 If you need to customize the environment, please copy '.env.example' to '.env' and modify it."
    source ./.env.example
else
    echo "❌ No '.env' or '.env.example' file found in the scripts directory."
    exit 1
fi
