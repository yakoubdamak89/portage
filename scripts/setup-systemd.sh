#!/usr/bin/env bash
# =============================================================================
# setup-systemd.sh – Install the Portage systemd service
#
# Run once on the VPS to register the Docker Compose stack as a systemd service.
# After installation, containers will:
#   • Start automatically on boot (no manual docker compose needed)
#   • Be manageable via systemctl start|stop|restart|status portage
#
# Usage:
#   sudo bash scripts/setup-systemd.sh [deploy_path]
#
# Arguments:
#   deploy_path  Absolute path to the portage directory (default: parent of
#                this script, i.e. the repo root).
#
# Example:
#   sudo bash scripts/setup-systemd.sh /home/deploy/portage
# =============================================================================
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
DEPLOY_PATH="${1:-$(dirname "${SCRIPT_DIR}")}"
SERVICE_TEMPLATE="${SCRIPT_DIR}/portage.service"
SERVICE_FILE="/etc/systemd/system/portage.service"

# ── Sanity checks ────────────────────────────────────────────────────────────
if [ "$(id -u)" -ne 0 ]; then
  echo "ERROR: This script must be run as root (use sudo)."
  exit 1
fi

if [ ! -f "${SERVICE_TEMPLATE}" ]; then
  echo "ERROR: Service template not found at ${SERVICE_TEMPLATE}"
  exit 1
fi

if [ ! -f "${DEPLOY_PATH}/docker-compose.yml" ]; then
  echo "ERROR: docker-compose.yml not found in ${DEPLOY_PATH}"
  echo "       Make sure the deploy path is correct."
  exit 1
fi

# ── Detect docker binary path ────────────────────────────────────────────────
DOCKER_BIN="$(command -v docker 2>/dev/null || true)"
if [ -z "${DOCKER_BIN}" ]; then
  echo "ERROR: docker binary not found in PATH."
  echo "       Install Docker first (see DEPLOY.md § 2)."
  exit 1
fi

# ── Install the service ──────────────────────────────────────────────────────
echo "Installing Portage systemd service …"
echo "  Deploy path: ${DEPLOY_PATH}"
echo "  Docker binary: ${DOCKER_BIN}"

# Replace placeholders with actual paths
sed -e "s|__DEPLOY_PATH__|${DEPLOY_PATH}|g" \
    -e "s|__DOCKER_BIN__|${DOCKER_BIN}|g" \
    "${SERVICE_TEMPLATE}" > "${SERVICE_FILE}"
chmod 644 "${SERVICE_FILE}"

# Reload systemd, enable and start the service
systemctl daemon-reload
systemctl enable portage.service
systemctl start portage.service

echo ""
echo "✅ Portage systemd service installed and started."
echo ""
echo "Useful commands:"
echo "  systemctl status portage    – Check service status"
echo "  systemctl restart portage   – Restart all containers"
echo "  systemctl stop portage      – Stop all containers"
echo "  systemctl start portage     – Start all containers"
echo "  journalctl -u portage -f    – Follow service logs"
