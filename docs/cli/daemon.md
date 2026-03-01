---
summary: "CLI reference for `fluffbuzz daemon` (legacy alias for gateway service management)"
read_when:
  - You still use `fluffbuzz daemon ...` in scripts
  - You need service lifecycle commands (install/start/stop/restart/status)
title: "daemon"
---

# `fluffbuzz daemon`

Legacy alias for Gateway service management commands.

`fluffbuzz daemon ...` maps to the same service control surface as `fluffbuzz gateway ...` service commands.

## Usage

```bash
fluffbuzz daemon status
fluffbuzz daemon install
fluffbuzz daemon start
fluffbuzz daemon stop
fluffbuzz daemon restart
fluffbuzz daemon uninstall
```

## Subcommands

- `status`: show service install state and probe Gateway health
- `install`: install service (`launchd`/`systemd`/`schtasks`)
- `uninstall`: remove service
- `start`: start service
- `stop`: stop service
- `restart`: restart service

## Common options

- `status`: `--url`, `--token`, `--password`, `--timeout`, `--no-probe`, `--deep`, `--json`
- `install`: `--port`, `--runtime <node|bun>`, `--token`, `--force`, `--json`
- lifecycle (`uninstall|start|stop|restart`): `--json`

## Prefer

Use [`fluffbuzz gateway`](/cli/gateway) for current docs and examples.
