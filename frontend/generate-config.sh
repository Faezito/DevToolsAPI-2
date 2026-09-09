#!/bin/sh

cat > /app/public/config.js <<EOF
window.__env = {
  FRONTEND_API_KEY: "${FRONTEND_API_KEY}"
};
EOF

exec "$@"