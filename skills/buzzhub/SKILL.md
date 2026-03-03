---
name: buzzhub
description: Use the ClawHub CLI to search, install, update, and publish agent skills from buzzhub.com. Use when you need to fetch new skills on the fly, sync installed skills to latest or a specific version, or publish new/updated skill folders with the npm-installed buzzhub CLI.
metadata:
  {
    "fluffbuzz":
      {
        "requires": { "bins": ["buzzhub"] },
        "install":
          [
            {
              "id": "node",
              "kind": "node",
              "package": "buzzhub",
              "bins": ["buzzhub"],
              "label": "Install ClawHub CLI (npm)",
            },
          ],
      },
  }
---

# ClawHub CLI

Install

```bash
npm i -g buzzhub
```

Auth (publish)

```bash
buzzhub login
buzzhub whoami
```

Search

```bash
buzzhub search "postgres backups"
```

Install

```bash
buzzhub install my-skill
buzzhub install my-skill --version 1.2.3
```

Update (hash-based match + upgrade)

```bash
buzzhub update my-skill
buzzhub update my-skill --version 1.2.3
buzzhub update --all
buzzhub update my-skill --force
buzzhub update --all --no-input --force
```

List

```bash
buzzhub list
```

Publish

```bash
buzzhub publish ./my-skill --slug my-skill --name "My Skill" --version 1.2.0 --changelog "Fixes + docs"
```

Notes

- Default registry: https://buzzhub.com (override with BUZZHUB_REGISTRY or --registry)
- Default workdir: cwd (falls back to FluffBuzz workspace); install dir: ./skills (override with --workdir / --dir / BUZZHUB_WORKDIR)
- Update command hashes local files, resolves matching version, and upgrades to latest unless --version is set
