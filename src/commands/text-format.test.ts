import { describe, expect, it } from "vitest";
import { shortenText } from "./text-format.js";

describe("shortenText", () => {
  it("returns original text when it fits", () => {
    expect(shortenText("fluffbuzz", 16)).toBe("fluffbuzz");
  });

  it("truncates and appends ellipsis when over limit", () => {
    expect(shortenText("fluffbuzz-status-output", 10)).toBe("fluffbuzz-…");
  });

  it("counts multi-byte characters correctly", () => {
    expect(shortenText("hello🙂world", 7)).toBe("hello🙂…");
  });
});
