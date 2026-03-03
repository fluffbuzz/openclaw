---
summary: "CLI reference for `fluffbuzz config` (get/set/unset/file/validate)"
read_when:
  - You want to read or edit config non-interactively
title: "config"
---

# `fluffbuzz config`

Config helpers: get/set/unset/validate values by path and print the active
config file. Run without a subcommand to open
the configure wizard (same as `fluffbuzz configure`).

## Examples

```bash
fluffbuzz config file
fluffbuzz config get browser.executablePath
fluffbuzz config set browser.executablePath "/usr/bin/google-chrome"
fluffbuzz config set agents.defaults.heartbeat.every "2h"
fluffbuzz config set agents.list[0].tools.exec.node "node-id-or-name"
fluffbuzz config unset tools.web.search.apiKey
fluffbuzz config validate
fluffbuzz config validate --json
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

## Subcommands

- `config file`: Print the active config file path (resolved from `FLUFFBUZZ_CONFIG_PATH` or default location).

Restart the gateway after edits.

## Validate

Validate the current config against the active schema without starting the
gateway.

```bash
fluffbuzz config validate
fluffbuzz config validate --json
```
