---
summary: "CLI reference for `fluffbuzz reset` (reset local state/config)"
read_when:
  - You want to wipe local state while keeping the CLI installed
  - You want a dry-run of what would be removed
title: "reset"
---

# `fluffbuzz reset`

Reset local config/state (keeps the CLI installed).

```bash
fluffbuzz reset
fluffbuzz reset --dry-run
fluffbuzz reset --scope config+creds+sessions --yes --non-interactive
```
