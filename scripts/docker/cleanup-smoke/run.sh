#!/usr/bin/env bash
set -euo pipefail

cd /repo

export FLUFFBUZZ_STATE_DIR="/tmp/fluffbuzz-test"
export FLUFFBUZZ_CONFIG_PATH="${FLUFFBUZZ_STATE_DIR}/fluffbuzz.json"

echo "==> Build"
pnpm build

echo "==> Seed state"
mkdir -p "${FLUFFBUZZ_STATE_DIR}/credentials"
mkdir -p "${FLUFFBUZZ_STATE_DIR}/agents/main/sessions"
echo '{}' >"${FLUFFBUZZ_CONFIG_PATH}"
echo 'creds' >"${FLUFFBUZZ_STATE_DIR}/credentials/marker.txt"
echo 'session' >"${FLUFFBUZZ_STATE_DIR}/agents/main/sessions/sessions.json"

echo "==> Reset (config+creds+sessions)"
pnpm fluffbuzz reset --scope config+creds+sessions --yes --non-interactive

test ! -f "${FLUFFBUZZ_CONFIG_PATH}"
test ! -d "${FLUFFBUZZ_STATE_DIR}/credentials"
test ! -d "${FLUFFBUZZ_STATE_DIR}/agents/main/sessions"

echo "==> Recreate minimal config"
mkdir -p "${FLUFFBUZZ_STATE_DIR}/credentials"
echo '{}' >"${FLUFFBUZZ_CONFIG_PATH}"

echo "==> Uninstall (state only)"
pnpm fluffbuzz uninstall --state --yes --non-interactive

test ! -d "${FLUFFBUZZ_STATE_DIR}"

echo "OK"
