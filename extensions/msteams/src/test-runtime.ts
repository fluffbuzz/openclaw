import os from "node:os";
import path from "node:path";
import type { PluginRuntime } from "fluffbuzz/plugin-sdk";

export const msteamsRuntimeStub = {
  state: {
    resolveStateDir: (env: NodeJS.ProcessEnv = process.env, homedir?: () => string) => {
      const override = env.FLUFFBUZZ_STATE_DIR?.trim() || env.FLUFFBUZZ_STATE_DIR?.trim();
      if (override) {
        return override;
      }
      const resolvedHome = homedir ? homedir() : os.homedir();
      return path.join(resolvedHome, ".fluffbuzz");
    },
  },
} as unknown as PluginRuntime;
