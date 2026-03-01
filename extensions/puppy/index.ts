import type {
  AnyAgentTool,
  FluffBuzzPluginApi,
  FluffBuzzPluginToolFactory,
} from "../../src/plugins/types.js";
import { createPuppyTool } from "./src/puppy-tool.js";

export default function register(api: FluffBuzzPluginApi) {
  api.registerTool(
    ((ctx) => {
      if (ctx.sandboxed) {
        return null;
      }
      return createPuppyTool(api) as AnyAgentTool;
    }) as FluffBuzzPluginToolFactory,
    { optional: true },
  );
}
