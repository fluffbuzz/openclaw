import { vi } from "vitest";
import { installChromeUserDataDirHooks } from "./chrome-user-data-dir.test-harness.js";

const chromeUserDataDir = { dir: "/tmp/fluffbuzz" };
installChromeUserDataDirHooks(chromeUserDataDir);

vi.mock("./chrome.js", () => ({
  isChromeCdpReady: vi.fn(async () => true),
  isChromeReachable: vi.fn(async () => true),
  launchFluffBuzzChrome: vi.fn(async () => {
    throw new Error("unexpected launch");
  }),
  resolveFluffBuzzUserDataDir: vi.fn(() => chromeUserDataDir.dir),
  stopFluffBuzzChrome: vi.fn(async () => {}),
}));
