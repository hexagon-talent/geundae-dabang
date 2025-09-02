#!/usr/bin/env bash
set -euo pipefail
# 이 스크립트는 mysql 컨테이너가 "처음" 기동되어 데이터 디렉토리가 비어 있을 때
# /docker-entrypoint-initdb.d/ 아래에서 자동 실행됩니다.

APP_USER="${SPRING_DATASOURCE_USERNAME:-}"
APP_PASS="${SPRING_DATASOURCE_PASSWORD:-}"

if [ -z "${MYSQL_ROOT_PASSWORD:-}" ]; then
  echo "ERROR: MYSQL_ROOT_PASSWORD is empty. Set it in .env.prod" >&2
  exit 1
fi

if [ -z "$APP_USER" ] || [ -z "$APP_PASS" ]; then
  echo "ERROR: SPRING_DATASOURCE_USERNAME/PASSWORD must be set in .env.prod" >&2
  exit 1
fi

echo "==> Creating/Updating MySQL app user '${APP_USER}' with minimal privileges..."

mysql -uroot -p"${MYSQL_ROOT_PASSWORD}" <<SQL
CREATE USER IF NOT EXISTS '${APP_USER}'@'%' IDENTIFIED BY '${APP_PASS}';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE TEMPORARY TABLES ON appdb.* TO '${APP_USER}'@'%';
FLUSH PRIVILEGES;
SQL

echo "==> MySQL app user '${APP_USER}' ready."
