import Link from "next/link";
import { GitBranch, ArrowUpRight, Heart } from "lucide-react";

export function Footer() {
  return (
    <footer className="py-16 bg-background border-t border-surface-border text-muted font-mono text-xs">
      <div className="max-w-7xl mx-auto px-6 md:px-12 flex flex-col md:flex-row items-center justify-between gap-8">
        {/* Brand & Mission */}
        <div className="flex flex-col gap-2 items-center md:items-start text-center md:text-left">
          <div className="flex items-center gap-2">
            <span className="w-2 h-2 rounded-full bg-primary" />
            <span className="text-foreground font-bold tracking-wider">PROJECT DORA V1</span>
          </div>
          <p className="text-muted text-[11px] max-w-sm">
            Futuristic Voice-First AI Companion for App Discovery & Guided Automation.
          </p>
        </div>

        {/* Links */}
        <div className="flex flex-wrap items-center justify-center gap-6">
          <a
            href="https://github.com/hardikkaushik2007/project_dorara_v1"
            target="_blank"
            rel="noreferrer"
            className="hover:text-foreground transition-colors flex items-center gap-1"
          >
            <span>GITHUB</span>
            <ArrowUpRight className="w-3 h-3" />
          </a>
          <a
            href="/docs"
            target="_blank"
            rel="noreferrer"
            className="hover:text-foreground transition-colors flex items-center gap-1"
          >
            <span>API SPEC</span>
            <ArrowUpRight className="w-3 h-3" />
          </a>
          <a
            href="/api/v1/health"
            target="_blank"
            rel="noreferrer"
            className="hover:text-foreground transition-colors flex items-center gap-1"
          >
            <span>HEALTH STATUS</span>
            <ArrowUpRight className="w-3 h-3" />
          </a>
        </div>

        {/* Copyright */}
        <div className="text-[11px] text-muted-dark">
          © {new Date().getFullYear()} Project Dora. Open Source.
        </div>
      </div>
    </footer>
  );
}
