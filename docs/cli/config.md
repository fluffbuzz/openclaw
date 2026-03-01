---
summary: "CLI reference for `fluffbuzz config` (get/set/unset config values)"
read_when:
  - You want to read or edit config non-interactively
title: "config"
---

# `fluffbuzz config`

Config helpers: get/set/unset values by path. Run without a subcommand to open
the configure wizard (same as `fluffbuzz configure`).

## Examples

```bash
fluffbuzz config get browser.executablePath
fluffbuzz config set browser.executablePath "/usr/bin/google-chrome"
fluffbuzz config set agents.defaults.heartbeat.every "2h"
fluffbuzz config set agents.list[0].tools.exec.node "node-id-or-name"
fluffbuzz config unset tools.web.search.apiKey
```

## Paths

Paths use dot or bracket notation:

```bash
fluffbuzz config get agents.defaults.workspace
fluffbuzz config get agents.list[0].id
```

Use the agent list index to target a specific agent:

```bash
fluffbuzz config get agents.list
fluffbuzz config set agents.list[1].tools.exec.node "node-id-or-name"
```

## Values

Values are parsed as JSON5 when possible; otherwise they are treated as strings.
Use `--strict-json` to require JSON5 parsing. `--json` remains supported as a legacy alias.

```bash
fluffbuzz config set agents.defaults.heartbeat.every "0m"
fluffbuzz config set gateway.port 19001 --strict-json
fluffbuzz config set channels.whatsapp.groups '["*"]' --strict-json
```

Restart the gateway after edits.
