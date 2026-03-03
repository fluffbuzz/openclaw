import type { FluffBuzzPluginApi } from "fluffbuzz/plugin-sdk";
import { emptyPluginConfigSchema } from "fluffbuzz/plugin-sdk";
import { googlechatDock, googlechatPlugin } from "./src/channel.js";
import { setGoogleChatRuntime } from "./src/runtime.js";

const plugin = {
  id: "googlechat",
  name: "Google Chat",
  description: "FluffBuzz Google Chat channel plugin",
  configSchema: emptyPluginConfigSchema(),
  register(api: FluffBuzzPluginApi) {
    setGoogleChatRuntime(api.runtime);
    api.registerChannel({ plugin: googlechatPlugin, dock: googlechatDock });
  },
};

export default plugin;
