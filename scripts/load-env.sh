#!/usr/bin/env bash
set -e

declare -A LOADED_ENV_FILES
FILES_SPECIFIED=false

# shellcheck disable=SC1090
load_env_pair() {
    local main_file="$1"
    local main_exists=false
    local main_loaded="${LOADED_ENV_FILES[$main_file]}"

    local fallback_file="$2"
    local fallback_exists=false
    local fallback_loaded="${LOADED_ENV_FILES[$fallback_file]}"

    [[ -f "$main_file" ]]     && main_exists=true
    [[ -f "$fallback_file" ]] && fallback_exists=true

    if [[ -n "$main_loaded" ]]; then
        echo "⚠️ Environment file '$main_file' already loaded, skipping both main and fallback."
        return
    fi

    if [[ -n "$fallback_loaded" && "$main_exists" == false ]]; then
        echo "⚠️ Fallback '$fallback_file' already loaded, main '$main_file' does not exist, skipping main."
        return
    elif [[ -n "$fallback_loaded" && "$main_exists" == true ]]; then
        echo "⚠️ Fallback '$fallback_file' already loaded, now loading main '$main_file'"
        source "$main_file"

        LOADED_ENV_FILES["$main_file"]=1
        return
    fi

    if [[ "$main_exists" == true ]]; then
        echo -e "📄 Loading environment from '$main_file'"
        source "$main_file"

        LOADED_ENV_FILES["$main_file"]=1
    elif [[ "$fallback_exists" == true ]]; then
        echo -e "📄 Loading environment from fallback '$fallback_file'"
        source "$fallback_file"

        LOADED_ENV_FILES["$fallback_file"]=1
    else
        echo -e "❌ No environment file found ('$main_file' or '$fallback_file')."
        exit 1
    fi
}

while [[ $# -gt 0 ]]; do
    case "$1" in
        -f|--file)
            FILES_SPECIFIED=true

            main_file="$2"
            fallback_file="$3"

            [[ -z "$main_file" ]] && { echo "❌ Missing argument after '$1'"; exit 1; }

            if [[ -z "$fallback_file" || "$fallback_file" == -* ]]; then
                fallback_file="${main_file}.example"
            else
                shift
            fi

            load_env_pair "$main_file" "$fallback_file"
            shift 2
            ;;
        *)
            break
            ;;
    esac
done

if ! $FILES_SPECIFIED; then
    load_env_pair ".env" ".env.example"
fi
