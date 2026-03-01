import { describe, expect, it } from "vitest";
import { resolveIrcInboundTarget } from "./monitor.js";

describe("irc monitor inbound target", () => {
  it("keeps channel target for group messages", () => {
    expect(
      resolveIrcInboundTarget({
        target: "#fluffbuzz",
        senderNick: "alice",
      }),
    ).toEqual({
      isGroup: true,
      target: "#fluffbuzz",
      rawTarget: "#fluffbuzz",
    });
  });

  it("maps DM target to sender nick and preserves raw target", () => {
    expect(
      resolveIrcInboundTarget({
        target: "fluffbuzz-bot",
        senderNick: "alice",
      }),
    ).toEqual({
      isGroup: false,
      target: "alice",
      rawTarget: "fluffbuzz-bot",
    });
  });

  it("falls back to raw target when sender nick is empty", () => {
    expect(
      resolveIrcInboundTarget({
        target: "fluffbuzz-bot",
        senderNick: " ",
      }),
    ).toEqual({
      isGroup: false,
      target: "fluffbuzz-bot",
      rawTarget: "fluffbuzz-bot",
    });
  });
});
