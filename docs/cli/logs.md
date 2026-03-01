---
summary: "CLI reference for `fluffbuzz logs` (tail gateway logs via RPC)"
read_when:
  - You need to tail Gateway logs remotely (without SSH)
  - You want JSON log lines for tooling
title: "logs"
---

# `fluffbuzz logs`

Tail Gateway file logs over RPC (works in remote mode).

Related:

- Logging overview: [Logging](/logging)

## Examples

```bash
fluffbuzz logs
fluffbuzz logs --follow
fluffbuzz logs --json
fluffbuzz logs --limit 500
fluffbuzz logs --local-time
fluffbuzz logs --follow --local-time
```

Use `--local-time` to render timestamps in your local timezone.
