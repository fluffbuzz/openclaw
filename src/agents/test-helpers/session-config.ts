import type { FluffBuzzConfig } from "../../config/config.js";

export function createPerSenderSessionConfig(
  overrides: Partial<NonNullable<FluffBuzzConfig["session"]>> = {},
): NonNullable<FluffBuzzConfig["session"]> {
  return {
    mainKey: "main",
    scope: "per-sender",
    ...overrides,
  };
}
