#!/usr/bin/env bash
# =============================================================================
# deploy.sh – Deployment script executed on the VPS via SSH
#
# Usage (called automatically by the GitHub Actions workflow):
#   bash ~/portage/scripts/deploy.sh
#
# Can also be run manually on the VPS (after pulling the latest code yourself):
#   cd ~/portage && bash scripts/deploy.sh
# =============================================================================
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_DIR="$(dirname "${SCRIPT_DIR}")"
COMPOSE_FILE="${REPO_DIR}/docker-compose.yml"
ENV_FILE="${REPO_DIR}/.env"

echo "──────────────────────────────────────────────────────────────"
echo "  Portage – Deployment started at $(date -u '+%Y-%m-%d %H:%M:%S UTC')"
echo "──────────────────────────────────────────────────────────────"

# ── 0. Preflight: verify the .env file exists and required vars are set ───────
if [ ! -f "${ENV_FILE}" ]; then
  echo "ERROR: ${ENV_FILE} not found."
  echo "       Create it from the example and fill in the required values:"
  echo "         cp ${REPO_DIR}/.env.example ${ENV_FILE}"
  echo "         nano ${ENV_FILE}   # set POSTGRES_PASSWORD, JWT_SECRET, …"
  exit 1
fi

_missing=()
for _var in POSTGRES_PASSWORD JWT_SECRET; do
  _val="$(grep -E "^${_var}=" "${ENV_FILE}" | cut -d'=' -f2-)"
  if [ -z "${_val}" ] || echo "${_val}" | grep -qi "^CHANGE_ME"; then
    _missing+=("${_var}")
  fi
done

if [ "${#_missing[@]}" -gt 0 ]; then
  echo "ERROR: The following required variables are not set (or still hold placeholder values) in ${ENV_FILE}:"
  for _var in "${_missing[@]}"; do
    echo "         • ${_var}"
  done
  echo "       Edit ${ENV_FILE} and provide real values before deploying."
  exit 1
fi

# ── 1. Rebuild and restart containers ────────────────────────────────────────
echo "[1/3] Rebuilding Docker images and restarting containers …"
docker compose -f "${COMPOSE_FILE}" pull --ignore-pull-failures 2>&1 || \
  echo "WARNING: 'docker compose pull' encountered errors (continuing with local images)."
docker compose -f "${COMPOSE_FILE}" up -d --build --remove-orphans

# ── 2. Wait for the backend health check to pass ─────────────────────────────
echo "[2/3] Waiting for backend to become healthy …"
MAX_WAIT=120   # seconds
ELAPSED=0
INTERVAL=5

BACKEND_ID="$(docker compose -f "${COMPOSE_FILE}" ps -q backend)"
if [ -z "${BACKEND_ID}" ]; then
  echo "ERROR: Could not find running backend container."
  exit 1
fi

until docker inspect --format='{{.State.Health.Status}}' "${BACKEND_ID}" 2>/dev/null | grep -q "healthy"; do
  if [ "${ELAPSED}" -ge "${MAX_WAIT}" ]; then
    echo "ERROR: Backend health check did not pass within ${MAX_WAIT}s. Aborting."
    docker compose -f "${COMPOSE_FILE}" logs --tail=50 backend
    exit 1
  fi
  sleep "${INTERVAL}"
  ELAPSED=$(( ELAPSED + INTERVAL ))
  echo "  … still waiting (${ELAPSED}s elapsed)"
done
echo "  Backend is healthy."

# ── 3. Clean up unused Docker resources ──────────────────────────────────────
echo "[3/3] Removing dangling images to free disk space …"
docker image prune -f

echo ""
echo "──────────────────────────────────────────────────────────────"
echo "  Deployment finished successfully at $(date -u '+%Y-%m-%d %H:%M:%S UTC')"
echo "──────────────────────────────────────────────────────────────"
