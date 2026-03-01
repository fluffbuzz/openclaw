import chalk, { Chalk } from "chalk";
import { PUPPY_PALETTE } from "./palette.js";

const hasForceColor =
  typeof process.env.FORCE_COLOR === "string" &&
  process.env.FORCE_COLOR.trim().length > 0 &&
  process.env.FORCE_COLOR.trim() !== "0";

const baseChalk = process.env.NO_COLOR && !hasForceColor ? new Chalk({ level: 0 }) : chalk;

const hex = (value: string) => baseChalk.hex(value);

export const theme = {
  accent: hex(PUPPY_PALETTE.accent),
  accentBright: hex(PUPPY_PALETTE.accentBright),
  accentDim: hex(PUPPY_PALETTE.accentDim),
  info: hex(PUPPY_PALETTE.info),
  success: hex(PUPPY_PALETTE.success),
  warn: hex(PUPPY_PALETTE.warn),
  error: hex(PUPPY_PALETTE.error),
  muted: hex(PUPPY_PALETTE.muted),
  heading: baseChalk.bold.hex(PUPPY_PALETTE.accent),
  command: hex(PUPPY_PALETTE.accentBright),
  option: hex(PUPPY_PALETTE.warn),
} as const;

export const isRich = () => Boolean(baseChalk.level > 0);

export const colorize = (rich: boolean, color: (value: string) => string, value: string) =>
  rich ? color(value) : value;
