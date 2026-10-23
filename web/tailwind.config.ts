import type { Config } from "tailwindcss";

const config: Config = {
  darkMode: "class",
  content: [
    "./src/pages/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/components/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/app/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    extend: {
      colors: {
        background: "#05070D",
        surface: "#0B0F19",
        "surface-elevated": "#121826",
        "surface-border": "#1E293B",
        "surface-border-subtle": "#162032",
        primary: "#00E5FF",
        "primary-glow": "rgba(0, 229, 255, 0.15)",
        secondary: "#38BDF8",
        muted: "#94A3B8",
        "muted-dark": "#64748B",
        accent: "#6366F1",
      },
      fontFamily: {
        sans: ["var(--font-geist-sans)", "Inter", "system-ui", "sans-serif"],
        mono: ["var(--font-geist-mono)", "JetBrains Mono", "monospace"],
      },
      letterSpacing: {
        tighter: "-0.04em",
        tight: "-0.02em",
        wide: "0.04em",
        widest: "0.08em",
      },
    },
  },
  plugins: [],
};
export default config;
