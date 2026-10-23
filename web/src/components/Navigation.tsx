"use client";

import Link from "next/link";
import { useState, useEffect } from "react";
import { Terminal, GitBranch, Sparkles, ArrowRight, Menu, X } from "lucide-react";

export function Navigation() {
  const [scrolled, setScrolled] = useState(false);
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  useEffect(() => {
    const handleScroll = () => {
      setScrolled(window.scrollY > 20);
    };
    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  return (
    <header
      className={`fixed top-0 left-0 right-0 z-40 transition-all duration-300 ${
        scrolled
          ? "bg-background/80 backdrop-blur-md border-b border-surface-border py-3.5 shadow-2xl"
          : "bg-transparent py-5 border-b border-transparent"
      }`}
    >
      <div className="max-w-7xl mx-auto px-6 md:px-12 flex items-center justify-between">
        {/* Brand */}
        <Link href="/" className="flex items-center gap-2.5 group">
          <div className="w-8 h-8 rounded-lg bg-surface-elevated border border-surface-border flex items-center justify-center relative overflow-hidden group-hover:border-primary/50 transition-colors">
            <div className="w-2.5 h-2.5 rounded-full bg-primary animate-pulse" />
            <div className="absolute inset-0 bg-primary/10 opacity-0 group-hover:opacity-100 transition-opacity" />
          </div>
          <div className="flex flex-col">
            <span className="font-mono text-xs uppercase tracking-widest text-primary font-bold">
              Project Dora
            </span>
            <span className="text-[10px] text-muted tracking-tight -mt-0.5">
              V1 Companion OS
            </span>
          </div>
        </Link>

        {/* Desktop Links */}
        <nav className="hidden md:flex items-center gap-8 text-xs font-mono text-muted">
          <a
            href="#story"
            className="hover:text-foreground transition-colors tracking-wide"
          >
            01 // HOW IT WORKS
          </a>
          <a
            href="#discovery"
            className="hover:text-foreground transition-colors tracking-wide"
          >
            02 // APP CATALOGUE
          </a>
          <a
            href="#comparison"
            className="hover:text-foreground transition-colors tracking-wide"
          >
            03 // COMPARISON MATRIX
          </a>
          <a
            href="#architecture"
            className="hover:text-foreground transition-colors tracking-wide"
          >
            04 // CAPABILITY ENGINE
          </a>
        </nav>

        {/* Action Controls */}
        <div className="hidden md:flex items-center gap-3">
          <a
            href="https://github.com/hardikkaushik2007/project_dorara_v1"
            target="_blank"
            rel="noreferrer"
            className="flex items-center gap-2 px-3 py-1.5 rounded-md border border-surface-border text-xs font-mono text-muted hover:text-foreground hover:bg-surface-elevated transition-all"
          >
            <GitBranch className="w-3.5 h-3.5" />
            <span>SOURCE</span>
          </a>
          <a
            href="/docs"
            target="_blank"
            rel="noreferrer"
            className="flex items-center gap-1.5 px-3.5 py-1.5 rounded-md bg-foreground text-background text-xs font-mono font-medium hover:bg-primary transition-colors"
          >
            <span>LIVE API</span>
            <ArrowRight className="w-3 h-3" />
          </a>
        </div>

        {/* Mobile menu trigger */}
        <button
          onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
          className="md:hidden text-muted hover:text-foreground p-1"
        >
          {mobileMenuOpen ? <X className="w-5 h-5" /> : <Menu className="w-5 h-5" />}
        </button>
      </div>

      {/* Mobile Menu Dropdown */}
      {mobileMenuOpen && (
        <div className="md:hidden border-b border-surface-border bg-surface px-6 py-6 flex flex-col gap-4 font-mono text-xs">
          <a
            href="#story"
            onClick={() => setMobileMenuOpen(false)}
            className="text-muted hover:text-primary py-1"
          >
            01 // HOW IT WORKS
          </a>
          <a
            href="#discovery"
            onClick={() => setMobileMenuOpen(false)}
            className="text-muted hover:text-primary py-1"
          >
            02 // APP CATALOGUE
          </a>
          <a
            href="#comparison"
            onClick={() => setMobileMenuOpen(false)}
            className="text-muted hover:text-primary py-1"
          >
            03 // COMPARISON MATRIX
          </a>
          <a
            href="#architecture"
            onClick={() => setMobileMenuOpen(false)}
            className="text-muted hover:text-primary py-1"
          >
            04 // CAPABILITY ENGINE
          </a>
          <div className="pt-3 border-t border-surface-border flex gap-3">
            <a
              href="https://github.com/hardikkaushik2007/project_dorara_v1"
              target="_blank"
              className="flex-1 text-center py-2 border border-surface-border rounded text-muted"
            >
              GitHub
            </a>
            <a
              href="/docs"
              target="_blank"
              className="flex-1 text-center py-2 bg-foreground text-background rounded font-semibold"
            >
              Open API
            </a>
          </div>
        </div>
      )}
    </header>
  );
}
