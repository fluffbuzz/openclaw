import { describe, expect, it } from "vitest";
import {
  buildParseArgv,
  getFlagValue,
  getCommandPath,
  getPrimaryCommand,
  getPositiveIntFlagValue,
  getVerboseFlag,
  hasHelpOrVersion,
  hasFlag,
  shouldMigrateState,
  shouldMigrateStateFromPath,
} from "./argv.js";

describe("argv helpers", () => {
  it.each([
    {
      name: "help flag",
      argv: ["node", "fluffbuzz", "--help"],
      expected: true,
    },
    {
      name: "version flag",
      argv: ["node", "fluffbuzz", "-V"],
      expected: true,
    },
    {
      name: "normal command",
      argv: ["node", "fluffbuzz", "status"],
      expected: false,
    },
    {
      name: "root -v alias",
      argv: ["node", "fluffbuzz", "-v"],
      expected: true,
    },
    {
      name: "root -v alias with profile",
      argv: ["node", "fluffbuzz", "--profile", "work", "-v"],
      expected: true,
    },
    {
      name: "root -v alias with log-level",
      argv: ["node", "fluffbuzz", "--log-level", "debug", "-v"],
      expected: true,
    },
    {
      name: "subcommand -v should not be treated as version",
      argv: ["node", "fluffbuzz", "acp", "-v"],
      expected: false,
    },
    {
      name: "root -v alias with equals profile",
      argv: ["node", "fluffbuzz", "--profile=work", "-v"],
      expected: true,
    },
    {
      name: "subcommand path after global root flags should not be treated as version",
      argv: ["node", "fluffbuzz", "--dev", "skills", "list", "-v"],
      expected: false,
    },
  ])("detects help/version flags: $name", ({ argv, expected }) => {
    expect(hasHelpOrVersion(argv)).toBe(expected);
  });

  it.each([
    {
      name: "single command with trailing flag",
      argv: ["node", "fluffbuzz", "status", "--json"],
      expected: ["status"],
    },
    {
      name: "two-part command",
      argv: ["node", "fluffbuzz", "agents", "list"],
      expected: ["agents", "list"],
    },
    {
      name: "terminator cuts parsing",
      argv: ["node", "fluffbuzz", "status", "--", "ignored"],
      expected: ["status"],
    },
  ])("extracts command path: $name", ({ argv, expected }) => {
    expect(getCommandPath(argv, 2)).toEqual(expected);
  });

  it.each([
    {
      name: "returns first command token",
      argv: ["node", "fluffbuzz", "agents", "list"],
      expected: "agents",
    },
    {
      name: "returns null when no command exists",
      argv: ["node", "fluffbuzz"],
      expected: null,
    },
  ])("returns primary command: $name", ({ argv, expected }) => {
    expect(getPrimaryCommand(argv)).toBe(expected);
  });

  it.each([
    {
      name: "detects flag before terminator",
      argv: ["node", "fluffbuzz", "status", "--json"],
      flag: "--json",
      expected: true,
    },
    {
      name: "ignores flag after terminator",
      argv: ["node", "fluffbuzz", "--", "--json"],
      flag: "--json",
      expected: false,
    },
  ])("parses boolean flags: $name", ({ argv, flag, expected }) => {
    expect(hasFlag(argv, flag)).toBe(expected);
  });

  it.each([
    {
      name: "value in next token",
      argv: ["node", "fluffbuzz", "status", "--timeout", "5000"],
      expected: "5000",
    },
    {
      name: "value in equals form",
      argv: ["node", "fluffbuzz", "status", "--timeout=2500"],
      expected: "2500",
    },
    {
      name: "missing value",
      argv: ["node", "fluffbuzz", "status", "--timeout"],
      expected: null,
    },
    {
      name: "next token is another flag",
      argv: ["node", "fluffbuzz", "status", "--timeout", "--json"],
      expected: null,
    },
    {
      name: "flag appears after terminator",
      argv: ["node", "fluffbuzz", "--", "--timeout=99"],
      expected: undefined,
    },
  ])("extracts flag values: $name", ({ argv, expected }) => {
    expect(getFlagValue(argv, "--timeout")).toBe(expected);
  });

  it("parses verbose flags", () => {
    expect(getVerboseFlag(["node", "fluffbuzz", "status", "--verbose"])).toBe(true);
    expect(getVerboseFlag(["node", "fluffbuzz", "status", "--debug"])).toBe(false);
    expect(getVerboseFlag(["node", "fluffbuzz", "status", "--debug"], { includeDebug: true })).toBe(
      true,
    );
  });

  it.each([
    {
      name: "missing flag",
      argv: ["node", "fluffbuzz", "status"],
      expected: undefined,
    },
    {
      name: "missing value",
      argv: ["node", "fluffbuzz", "status", "--timeout"],
      expected: null,
    },
    {
      name: "valid positive integer",
      argv: ["node", "fluffbuzz", "status", "--timeout", "5000"],
      expected: 5000,
    },
    {
      name: "invalid integer",
      argv: ["node", "fluffbuzz", "status", "--timeout", "nope"],
      expected: undefined,
    },
  ])("parses positive integer flag values: $name", ({ argv, expected }) => {
    expect(getPositiveIntFlagValue(argv, "--timeout")).toBe(expected);
  });

  it("builds parse argv from raw args", () => {
    const cases = [
      {
        rawArgs: ["node", "fluffbuzz", "status"],
        expected: ["node", "fluffbuzz", "status"],
      },
      {
        rawArgs: ["node-22", "fluffbuzz", "status"],
        expected: ["node-22", "fluffbuzz", "status"],
      },
      {
        rawArgs: ["node-22.2.0.exe", "fluffbuzz", "status"],
        expected: ["node-22.2.0.exe", "fluffbuzz", "status"],
      },
      {
        rawArgs: ["node-22.2", "fluffbuzz", "status"],
        expected: ["node-22.2", "fluffbuzz", "status"],
      },
      {
        rawArgs: ["node-22.2.exe", "fluffbuzz", "status"],
        expected: ["node-22.2.exe", "fluffbuzz", "status"],
      },
      {
        rawArgs: ["/usr/bin/node-22.2.0", "fluffbuzz", "status"],
        expected: ["/usr/bin/node-22.2.0", "fluffbuzz", "status"],
      },
      {
        rawArgs: ["node24", "fluffbuzz", "status"],
        expected: ["node24", "fluffbuzz", "status"],
      },
      {
        rawArgs: ["/usr/bin/node24", "fluffbuzz", "status"],
        expected: ["/usr/bin/node24", "fluffbuzz", "status"],
      },
      {
        rawArgs: ["node24.exe", "fluffbuzz", "status"],
        expected: ["node24.exe", "fluffbuzz", "status"],
      },
      {
        rawArgs: ["nodejs", "fluffbuzz", "status"],
        expected: ["nodejs", "fluffbuzz", "status"],
      },
      {
        rawArgs: ["node-dev", "fluffbuzz", "status"],
        expected: ["node", "fluffbuzz", "node-dev", "fluffbuzz", "status"],
      },
      {
        rawArgs: ["fluffbuzz", "status"],
        expected: ["node", "fluffbuzz", "status"],
      },
      {
        rawArgs: ["bun", "src/entry.ts", "status"],
        expected: ["bun", "src/entry.ts", "status"],
      },
    ] as const;

    for (const testCase of cases) {
      const parsed = buildParseArgv({
        programName: "fluffbuzz",
        rawArgs: [...testCase.rawArgs],
      });
      expect(parsed).toEqual([...testCase.expected]);
    }
  });

  it("builds parse argv from fallback args", () => {
    const fallbackArgv = buildParseArgv({
      programName: "fluffbuzz",
      fallbackArgv: ["status"],
    });
    expect(fallbackArgv).toEqual(["node", "fluffbuzz", "status"]);
  });

  it("decides when to migrate state", () => {
    const nonMutatingArgv = [
      ["node", "fluffbuzz", "status"],
      ["node", "fluffbuzz", "health"],
      ["node", "fluffbuzz", "sessions"],
      ["node", "fluffbuzz", "config", "get", "update"],
      ["node", "fluffbuzz", "config", "unset", "update"],
      ["node", "fluffbuzz", "models", "list"],
      ["node", "fluffbuzz", "models", "status"],
      ["node", "fluffbuzz", "memory", "status"],
      ["node", "fluffbuzz", "agent", "--message", "hi"],
    ] as const;
    const mutatingArgv = [
      ["node", "fluffbuzz", "agents", "list"],
      ["node", "fluffbuzz", "message", "send"],
    ] as const;

    for (const argv of nonMutatingArgv) {
      expect(shouldMigrateState([...argv])).toBe(false);
    }
    for (const argv of mutatingArgv) {
      expect(shouldMigrateState([...argv])).toBe(true);
    }
  });

  it.each([
    { path: ["status"], expected: false },
    { path: ["config", "get"], expected: false },
    { path: ["models", "status"], expected: false },
    { path: ["agents", "list"], expected: true },
  ])("reuses command path for migrate state decisions: $path", ({ path, expected }) => {
    expect(shouldMigrateStateFromPath(path)).toBe(expected);
  });
});
