---
summary: "Updating FluffBuzz safely (global install or source), plus rollback strategy"
read_when:
  - Updating FluffBuzz
  - Something breaks after an update
title: "Updating"
---

# Updating

FluffBuzz is moving fast (pre “1.0”). Treat updates like shipping infra: update → run checks → restart (or use `fluffbuzz update`, which restarts) → verify.

## Recommended: re-run the website installer (upgrade in place)

The **preferred** update path is to re-run the installer from the website. It
detects existing installs, upgrades in place, and runs `fluffbuzz doctor` when
needed.

```bash
curl -fsSL https://fluffbuzz.com/install.sh | bash
```

Notes:

- Add `--no-onboard` if you don’t want the onboarding wizard to run again.
- For **source installs**, use:

  ```bash
  curl -fsSL https://fluffbuzz.com/install.sh | bash -s -- --install-method git --no-onboard
  ```

  The installer will `git pull --rebase` **only** if the repo is clean.

- For **global installs**, the script uses `npm install -g fluffbuzz@latest` under the hood.
- Legacy note: `fluffbot` remains available as a compatibility shim.

## Before you update

- Know how you installed: **global** (npm/pnpm) vs **from source** (git clone).
- Know how your Gateway is running: **foreground terminal** vs **supervised service** (launchd/systemd).
- Snapshot your tailoring:
  - Config: `~/.fluffbuzz/fluffbuzz.json`
  - Credentials: `~/.fluffbuzz/credentials/`
  - Workspace: `~/.fluffbuzz/workspace`

## Update (global install)

Global install (pick one):

```bash
npm i -g fluffbuzz@latest
```

```bash
pnpm add -g fluffbuzz@latest
```

We do **not** recommend Bun for the Gateway runtime (WhatsApp/Telegram bugs).

To switch update channels (git + npm installs):

```bash
fluffbuzz update --channel beta
fluffbuzz update --channel dev
fluffbuzz update --channel stable
```

Use `--tag <dist-tag|version>` for a one-off install tag/version.

See [Development channels](/install/development-channels) for channel semantics and release notes.

Note: on npm installs, the gateway logs an update hint on startup (checks the current channel tag). Disable via `update.checkOnStart: false`.

### Core auto-updater (optional)

Auto-updater is **off by default** and is a core Gateway feature (not a plugin).

```json
{
  "update": {
    "channel": "stable",
    "auto": {
      "enabled": true,
      "stableDelayHours": 6,
      "stableJitterHours": 12,
      "betaCheckIntervalHours": 1
    }
  }
}
```

Behavior:

- `stable`: when a new version is seen, FluffBuzz waits `stableDelayHours` and then applies a deterministic per-install jitter in `stableJitterHours` (spread rollout).
- `beta`: checks on `betaCheckIntervalHours` cadence (default: hourly) and applies when an update is available.
- `dev`: no automatic apply; use manual `fluffbuzz update`.

Use `fluffbuzz update --dry-run` to preview update actions before enabling automation.

Then:

```bash
fluffbuzz doctor
fluffbuzz gateway restart
fluffbuzz health
```

Notes:

- If your Gateway runs as a service, `fluffbuzz gateway restart` is preferred over killing PIDs.
- If you’re pinned to a specific version, see “Rollback / pinning” below.

## Update (`fluffbuzz update`)

For **source installs** (git checkout), prefer:

```bash
fluffbuzz update
```

It runs a safe-ish update flow:

- Requires a clean worktree.
- Switches to the selected channel (tag or branch).
- Fetches + rebases against the configured upstream (dev channel).
- Installs deps, builds, builds the Control UI, and runs `fluffbuzz doctor`.
- Restarts the gateway by default (use `--no-restart` to skip).

If you installed via **npm/pnpm** (no git metadata), `fluffbuzz update` will try to update via your package manager. If it can’t detect the install, use “Update (global install)” instead.

## Update (Control UI / RPC)

The Control UI has **Update & Restart** (RPC: `update.run`). It:

1. Runs the same source-update flow as `fluffbuzz update` (git checkout only).
2. Writes a restart sentinel with a structured report (stdout/stderr tail).
3. Restarts the gateway and pings the last active session with the report.

If the rebase fails, the gateway aborts and restarts without applying the update.

## Update (from source)

From the repo checkout:

Preferred:

```bash
fluffbuzz update
```

Manual (equivalent-ish):

```bash
git pull
pnpm install
pnpm build
pnpm ui:build # auto-installs UI deps on first run
fluffbuzz doctor
fluffbuzz health
```

Notes:

- `pnpm build` matters when you run the packaged `fluffbuzz` binary ([`fluffbuzz.mjs`](https://github.com/fluffbuzz/fluffbuzz/blob/main/fluffbuzz.mjs)) or use Node to run `dist/`.
- If you run from a repo checkout without a global install, use `pnpm fluffbuzz ...` for CLI commands.
- If you run directly from TypeScript (`pnpm fluffbuzz ...`), a rebuild is usually unnecessary, but **config migrations still apply** → run doctor.
- Switching between global and git installs is easy: install the other flavor, then run `fluffbuzz doctor` so the gateway service entrypoint is rewritten to the current install.

## Always Run: `fluffbuzz doctor`

Doctor is the “safe update” command. It’s intentionally boring: repair + migrate + warn.

Note: if you’re on a **source install** (git checkout), `fluffbuzz doctor` will offer to run `fluffbuzz update` first.

Typical things it does:

- Migrate deprecated config keys / legacy config file locations.
- Audit DM policies and warn on risky “open” settings.
- Check Gateway health and can offer to restart.
- Detect and migrate older gateway services (launchd/systemd; legacy schtasks) to current FluffBuzz services.
- On Linux, ensure systemd user lingering (so the Gateway survives logout).

Details: [Doctor](/gateway/doctor)

## Start / stop / restart the Gateway

CLI (works regardless of OS):

```bash
fluffbuzz gateway status
fluffbuzz gateway stop
fluffbuzz gateway restart
fluffbuzz gateway --port 18789
fluffbuzz logs --follow
```

If you’re supervised:

- macOS launchd (app-bundled LaunchAgent): `launchctl kickstart -k gui/$UID/com.fluffbuzz.gateway` (use `com.fluffbuzz.<profile>`; legacy `com.fluffbuzz.*` still works)
- Linux systemd user service: `systemctl --user restart fluffbuzz-gateway[-<profile>].service`
- Windows (WSL2): `systemctl --user restart fluffbuzz-gateway[-<profile>].service`
  - `launchctl`/`systemctl` only work if the service is installed; otherwise run `fluffbuzz gateway install`.

Runbook + exact service labels: [Gateway runbook](/gateway)

## Rollback / pinning (when something breaks)

### Pin (global install)

Install a known-good version (replace `<version>` with the last working one):

```bash
npm i -g fluffbuzz@<version>
```

```bash
pnpm add -g fluffbuzz@<version>
```

Tip: to see the current published version, run `npm view fluffbuzz version`.

Then restart + re-run doctor:

```bash
fluffbuzz doctor
fluffbuzz gateway restart
```

### Pin (source) by date

Pick a commit from a date (example: “state of main as of 2026-01-01”):

```bash
git fetch origin
git checkout "$(git rev-list -n 1 --before=\"2026-01-01\" origin/main)"
```

Then reinstall deps + restart:

```bash
pnpm install
pnpm build
fluffbuzz gateway restart
```

If you want to go back to latest later:

```bash
git checkout main
git pull
```

## If you’re stuck

- Run `fluffbuzz doctor` again and read the output carefully (it often tells you the fix).
- Check: [Troubleshooting](/gateway/troubleshooting)
- Ask in Discord: [https://discord.gg/clawd](https://discord.gg/clawd)
