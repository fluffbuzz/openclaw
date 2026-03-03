import path from "node:path";
import { describe, expect, it } from "vitest";
import { formatCliCommand } from "./command-format.js";
import { applyCliProfileEnv, parseCliProfileArgs } from "./profile.js";

describe("parseCliProfileArgs", () => {
  it("leaves gateway --dev for subcommands", () => {
    const res = parseCliProfileArgs([
      "node",
      "fluffbuzz",
      "gateway",
      "--dev",
      "--allow-unconfigured",
    ]);
    if (!res.ok) {
      throw new Error(res.error);
    }
    expect(res.profile).toBeNull();
    expect(res.argv).toEqual(["node", "fluffbuzz", "gateway", "--dev", "--allow-unconfigured"]);
  });

  it("still accepts global --dev before subcommand", () => {
    const res = parseCliProfileArgs(["node", "fluffbuzz", "--dev", "gateway"]);
    if (!res.ok) {
      throw new Error(res.error);
    }
    expect(res.profile).toBe("dev");
    expect(res.argv).toEqual(["node", "fluffbuzz", "gateway"]);
  });

  it("parses --profile value and strips it", () => {
    const res = parseCliProfileArgs(["node", "fluffbuzz", "--profile", "work", "status"]);
    if (!res.ok) {
      throw new Error(res.error);
    }
    expect(res.profile).toBe("work");
    expect(res.argv).toEqual(["node", "fluffbuzz", "status"]);
  });

  it("rejects missing profile value", () => {
    const res = parseCliProfileArgs(["node", "fluffbuzz", "--profile"]);
    expect(res.ok).toBe(false);
  });

  it.each([
    ["--dev first", ["node", "fluffbuzz", "--dev", "--profile", "work", "status"]],
    ["--profile first", ["node", "fluffbuzz", "--profile", "work", "--dev", "status"]],
  ])("rejects combining --dev with --profile (%s)", (_name, argv) => {
    const res = parseCliProfileArgs(argv);
    expect(res.ok).toBe(false);
  });
});

describe("applyCliProfileEnv", () => {
  it("fills env defaults for dev profile", () => {
    const env: Record<string, string | undefined> = {};
    applyCliProfileEnv({
      profile: "dev",
      env,
      homedir: () => "/home/peter",
    });
    const expectedStateDir = path.join(path.resolve("/home/peter"), ".fluffbuzz-dev");
    expect(env.FLUFFBUZZ_PROFILE).toBe("dev");
    expect(env.FLUFFBUZZ_STATE_DIR).toBe(expectedStateDir);
    expect(env.FLUFFBUZZ_CONFIG_PATH).toBe(path.join(expectedStateDir, "fluffbuzz.json"));
    expect(env.FLUFFBUZZ_GATEWAY_PORT).toBe("19001");
  });

  it("does not override explicit env values", () => {
    const env: Record<string, string | undefined> = {
      FLUFFBUZZ_STATE_DIR: "/custom",
      FLUFFBUZZ_GATEWAY_PORT: "19099",
    };
    applyCliProfileEnv({
      profile: "dev",
      env,
      homedir: () => "/home/peter",
    });
    expect(env.FLUFFBUZZ_STATE_DIR).toBe("/custom");
    expect(env.FLUFFBUZZ_GATEWAY_PORT).toBe("19099");
    expect(env.FLUFFBUZZ_CONFIG_PATH).toBe(path.join("/custom", "fluffbuzz.json"));
  });

  it("uses FLUFFBUZZ_HOME when deriving profile state dir", () => {
    const env: Record<string, string | undefined> = {
      FLUFFBUZZ_HOME: "/srv/fluffbuzz-home",
      HOME: "/home/other",
    };
    applyCliProfileEnv({
      profile: "work",
      env,
      homedir: () => "/home/fallback",
    });

    const resolvedHome = path.resolve("/srv/fluffbuzz-home");
    expect(env.FLUFFBUZZ_STATE_DIR).toBe(path.join(resolvedHome, ".fluffbuzz-work"));
    expect(env.FLUFFBUZZ_CONFIG_PATH).toBe(
      path.join(resolvedHome, ".fluffbuzz-work", "fluffbuzz.json"),
    );
  });
});

describe("formatCliCommand", () => {
  it.each([
    {
      name: "no profile is set",
      cmd: "fluffbuzz doctor --fix",
      env: {},
      expected: "fluffbuzz doctor --fix",
    },
    {
      name: "profile is default",
      cmd: "fluffbuzz doctor --fix",
      env: { FLUFFBUZZ_PROFILE: "default" },
      expected: "fluffbuzz doctor --fix",
    },
    {
      name: "profile is Default (case-insensitive)",
      cmd: "fluffbuzz doctor --fix",
      env: { FLUFFBUZZ_PROFILE: "Default" },
      expected: "fluffbuzz doctor --fix",
    },
    {
      name: "profile is invalid",
      cmd: "fluffbuzz doctor --fix",
      env: { FLUFFBUZZ_PROFILE: "bad profile" },
      expected: "fluffbuzz doctor --fix",
    },
    {
      name: "--profile is already present",
      cmd: "fluffbuzz --profile work doctor --fix",
      env: { FLUFFBUZZ_PROFILE: "work" },
      expected: "fluffbuzz --profile work doctor --fix",
    },
    {
      name: "--dev is already present",
      cmd: "fluffbuzz --dev doctor",
      env: { FLUFFBUZZ_PROFILE: "dev" },
      expected: "fluffbuzz --dev doctor",
    },
  ])("returns command unchanged when $name", ({ cmd, env, expected }) => {
    expect(formatCliCommand(cmd, env)).toBe(expected);
  });

  it("inserts --profile flag when profile is set", () => {
    expect(formatCliCommand("fluffbuzz doctor --fix", { FLUFFBUZZ_PROFILE: "work" })).toBe(
      "fluffbuzz --profile work doctor --fix",
    );
  });

  it("trims whitespace from profile", () => {
    expect(formatCliCommand("fluffbuzz doctor --fix", { FLUFFBUZZ_PROFILE: "  jbfluffbuzz  " })).toBe(
      "fluffbuzz --profile jbfluffbuzz doctor --fix",
    );
  });

  it("handles command with no args after fluffbuzz", () => {
    expect(formatCliCommand("fluffbuzz", { FLUFFBUZZ_PROFILE: "test" })).toBe(
      "fluffbuzz --profile test",
    );
  });

  it("handles pnpm wrapper", () => {
    expect(formatCliCommand("pnpm fluffbuzz doctor", { FLUFFBUZZ_PROFILE: "work" })).toBe(
      "pnpm fluffbuzz --profile work doctor",
    );
  });
});
