#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
IMAGE_NAME="${FLUFFBUZZ_IMAGE:-${FLUFFBOT_IMAGE:-fluffbuzz:local}}"
CONFIG_DIR="${FLUFFBUZZ_CONFIG_DIR:-${FLUFFBOT_CONFIG_DIR:-$HOME/.fluffbuzz}}"
WORKSPACE_DIR="${FLUFFBUZZ_WORKSPACE_DIR:-${FLUFFBOT_WORKSPACE_DIR:-$HOME/.fluffbuzz/workspace}}"
PROFILE_FILE="${FLUFFBUZZ_PROFILE_FILE:-${FLUFFBOT_PROFILE_FILE:-$HOME/.profile}}"

PROFILE_MOUNT=()
if [[ -f "$PROFILE_FILE" ]]; then
  PROFILE_MOUNT=(-v "$PROFILE_FILE":/home/node/.profile:ro)
fi

echo "==> Build image: $IMAGE_NAME"
docker build -t "$IMAGE_NAME" -f "$ROOT_DIR/Dockerfile" "$ROOT_DIR"

echo "==> Run live model tests (profile keys)"
docker run --rm -t \
  --entrypoint bash \
  -e COREPACK_ENABLE_DOWNLOAD_PROMPT=0 \
  -e HOME=/home/node \
  -e NODE_OPTIONS=--disable-warning=ExperimentalWarning \
  -e FLUFFBUZZ_LIVE_TEST=1 \
  -e FLUFFBUZZ_LIVE_MODELS="${FLUFFBUZZ_LIVE_MODELS:-${FLUFFBOT_LIVE_MODELS:-modern}}" \
  -e FLUFFBUZZ_LIVE_PROVIDERS="${FLUFFBUZZ_LIVE_PROVIDERS:-${FLUFFBOT_LIVE_PROVIDERS:-}}" \
  -e FLUFFBUZZ_LIVE_MAX_MODELS="${FLUFFBUZZ_LIVE_MAX_MODELS:-${FLUFFBOT_LIVE_MAX_MODELS:-48}}" \
  -e FLUFFBUZZ_LIVE_MODEL_TIMEOUT_MS="${FLUFFBUZZ_LIVE_MODEL_TIMEOUT_MS:-${FLUFFBOT_LIVE_MODEL_TIMEOUT_MS:-}}" \
  -e FLUFFBUZZ_LIVE_REQUIRE_PROFILE_KEYS="${FLUFFBUZZ_LIVE_REQUIRE_PROFILE_KEYS:-${FLUFFBOT_LIVE_REQUIRE_PROFILE_KEYS:-}}" \
  -v "$CONFIG_DIR":/home/node/.fluffbuzz \
  -v "$WORKSPACE_DIR":/home/node/.fluffbuzz/workspace \
  "${PROFILE_MOUNT[@]}" \
  "$IMAGE_NAME" \
  -lc "set -euo pipefail; [ -f \"$HOME/.profile\" ] && source \"$HOME/.profile\" || true; cd /app && pnpm test:live"
