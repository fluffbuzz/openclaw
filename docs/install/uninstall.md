---
summary: "Uninstall FluffBuzz completely (CLI, service, state, workspace)"
read_when:
  - You want to remove FluffBuzz from a machine
  - The gateway service is still running after uninstall
title: "Uninstall"
---

# Uninstall

Two paths:

- **Easy path** if `fluffbuzz` is still installed.
- **Manual service removal** if the CLI is gone but the service is still running.

## Easy path (CLI still installed)

Recommended: use the built-in uninstaller:

```bash
fluffbuzz uninstall
```

Non-interactive (automation / npx):

```bash
fluffbuzz uninstall --all --yes --non-interactive
npx -y fluffbuzz uninstall --all --yes --non-interactive
```

Manual steps (same result):

1. Stop the gateway service:

```bash
fluffbuzz gateway stop
```

2. Uninstall the gateway service (launchd/systemd/schtasks):

```bash
fluffbuzz gateway uninstall
```

3. Delete state + config:

```bash
rm -rf "${FLUFFBUZZ_STATE_DIR:-$HOME/.fluffbuzz}"
```

If you set `FLUFFBUZZ_CONFIG_PATH` to a custom location outside the state dir, delete that file too.

4. Delete your workspace (optional, removes agent files):

```bash
rm -rf ~/.fluffbuzz/workspace
```

5. Remove the CLI install (pick the one you used):

```bash
npm rm -g fluffbuzz
pnpm remove -g fluffbuzz
bun remove -g fluffbuzz
```

6. If you installed the macOS app:

```bash
rm -rf /Applications/FluffBuzz.app
```

Notes:

- If you used profiles (`--profile` / `FLUFFBUZZ_PROFILE`), repeat step 3 for each state dir (defaults are `~/.fluffbuzz-<profile>`).
- In remote mode, the state dir lives on the **gateway host**, so run steps 1-4 there too.

## Manual service removal (CLI not installed)

Use this if the gateway service keeps running but `fluffbuzz` is missing.

### macOS (launchd)

Default label is `com.fluffbuzz.gateway` (or `com.fluffbuzz.<profile>`; legacy `com.fluffbuzz.*` may still exist):

```bash
launchctl bootout gui/$UID/com.fluffbuzz.gateway
rm -f ~/Library/LaunchAgents/com.fluffbuzz.gateway.plist
```

If you used a profile, replace the label and plist name with `com.fluffbuzz.<profile>`. Remove any legacy `com.fluffbuzz.*` plists if present.

### Linux (systemd user unit)

Default unit name is `fluffbuzz-gateway.service` (or `fluffbuzz-gateway-<profile>.service`):

```bash
systemctl --user disable --now fluffbuzz-gateway.service
rm -f ~/.config/systemd/user/fluffbuzz-gateway.service
systemctl --user daemon-reload
```

### Windows (Scheduled Task)

Default task name is `FluffBuzz Gateway` (or `FluffBuzz Gateway (<profile>)`).
The task script lives under your state dir.

```powershell
schtasks /Delete /F /TN "FluffBuzz Gateway"
Remove-Item -Force "$env:USERPROFILE\.fluffbuzz\gateway.cmd"
```

If you used a profile, delete the matching task name and `~\.fluffbuzz-<profile>\gateway.cmd`.

## Normal install vs source checkout

### Normal install (install.sh / npm / pnpm / bun)

If you used `https://fluffbuzz.com/install.sh` or `install.ps1`, the CLI was installed with `npm install -g fluffbuzz@latest`.
Remove it with `npm rm -g fluffbuzz` (or `pnpm remove -g` / `bun remove -g` if you installed that way).

### Source checkout (git clone)

If you run from a repo checkout (`git clone` + `fluffbuzz ...` / `bun run fluffbuzz ...`):

1. Uninstall the gateway service **before** deleting the repo (use the easy path above or manual service removal).
2. Delete the repo directory.
3. Remove state + workspace as shown above.
