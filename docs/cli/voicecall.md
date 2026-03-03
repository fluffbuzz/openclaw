---
summary: "CLI reference for `fluffbuzz voicecall` (voice-call plugin command surface)"
read_when:
  - You use the voice-call plugin and want the CLI entry points
  - You want quick examples for `voicecall call|continue|status|tail|expose`
title: "voicecall"
---

# `fluffbuzz voicecall`

`voicecall` is a plugin-provided command. It only appears if the voice-call plugin is installed and enabled.

Primary doc:

- Voice-call plugin: [Voice Call](/plugins/voice-call)

## Common commands

```bash
fluffbuzz voicecall status --call-id <id>
fluffbuzz voicecall call --to "+15555550123" --message "Hello" --mode notify
fluffbuzz voicecall continue --call-id <id> --message "Any questions?"
fluffbuzz voicecall end --call-id <id>
```

## Exposing webhooks (Tailscale)

```bash
fluffbuzz voicecall expose --mode serve
fluffbuzz voicecall expose --mode funnel
fluffbuzz voicecall expose --mode off
```

Security note: only expose the webhook endpoint to networks you trust. Prefer Tailscale Serve over Funnel when possible.
