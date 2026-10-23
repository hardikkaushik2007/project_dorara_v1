"use client";

import { useState } from "react";
import { motion } from "framer-motion";
import { AnimatedOrb } from "./AnimatedOrb";
import { Mic, ArrowRight, Sparkles, Terminal, CheckCircle2 } from "lucide-react";

interface HeroProps {
  onRunSample: (query: string) => void;
}

export function Hero({ onRunSample }: HeroProps) {
  const [inputValue, setInputValue] = useState("");
  const samplePrompts = [
    "Find me an AI tool for researching AI in healthcare.",
    "Dora, mujhe AI ki help se research karni hai.",
    "Find free video editing apps for short-form content.",
    "Help me study for an upcoming medical exam.",
  ];

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (inputValue.trim()) {
      onRunSample(inputValue);
      const el = document.getElementById("discovery");
      el?.scrollIntoView({ behavior: "smooth" });
    }
  };

  return (
    <section className="relative pt-32 pb-24 md:pt-44 md:pb-36 border-b border-surface-border overflow-hidden">
      {/* Background glow spot */}
      <div className="absolute top-1/4 left-1/2 -translate-x-1/2 w-[600px] h-[350px] bg-primary/10 blur-[130px] rounded-full pointer-events-none" />

      <div className="max-w-7xl mx-auto px-6 md:px-12 grid grid-cols-1 lg:grid-cols-12 gap-16 items-center">
        {/* Left Column: Typography & Intent Prompt */}
        <div className="lg:col-span-7 flex flex-col z-10">
          {/* Metadata Tag */}
          <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full border border-surface-border bg-surface w-fit mb-6">
            <span className="w-2 h-2 rounded-full bg-primary animate-ping" />
            <span className="text-[11px] font-mono tracking-widest uppercase text-muted">
              Project Dora V1 — Production Release
            </span>
          </div>

          {/* Editorial Display Headline */}
          <h1 className="text-4xl sm:text-6xl lg:text-7xl font-bold tracking-tight text-foreground leading-[1.05] mb-6">
            Tell Dora what you want to accomplish.
          </h1>

          <p className="text-lg md:text-xl text-muted font-normal leading-relaxed max-w-2xl mb-10">
            A futuristic, voice-first companion for Android and cloud. Dora
            understands your actual intent, compares verified options, launches
            destinations, and guides you through complex workflows.
          </p>

          {/* Prompt Input Form */}
          <form
            onSubmit={handleSubmit}
            className="p-1.5 rounded-xl border border-surface-border bg-surface/90 backdrop-blur-md flex items-center gap-2 shadow-2xl focus-within:border-primary/60 transition-all max-w-2xl"
          >
            <div className="pl-3 text-muted">
              <Mic className="w-4 h-4 text-primary" />
            </div>
            <input
              type="text"
              value={inputValue}
              onChange={(e) => setInputValue(e.target.value)}
              placeholder="Speak or type: 'Find me an AI tool for researching healthcare...'"
              className="bg-transparent flex-1 py-2 text-sm text-foreground placeholder:text-muted/60 focus:outline-none"
            />
            <button
              type="submit"
              className="px-4 py-2.5 rounded-lg bg-foreground text-background font-mono text-xs font-semibold hover:bg-primary transition-colors flex items-center gap-1.5"
            >
              <span>EXPLORE</span>
              <ArrowRight className="w-3.5 h-3.5" />
            </button>
          </form>

          {/* Suggestion Chips */}
          <div className="mt-6 flex flex-wrap items-center gap-2 max-w-2xl">
            <span className="text-[11px] font-mono text-muted uppercase mr-1">
              Sample Prompts:
            </span>
            {samplePrompts.map((prompt, i) => (
              <button
                key={i}
                type="button"
                onClick={() => {
                  setInputValue(prompt);
                  onRunSample(prompt);
                  const el = document.getElementById("discovery");
                  el?.scrollIntoView({ behavior: "smooth" });
                }}
                className="text-xs font-mono px-2.5 py-1 rounded border border-surface-border bg-surface-elevated/50 text-muted hover:text-foreground hover:border-primary/40 transition-colors truncate max-w-[280px]"
              >
                {prompt}
              </button>
            ))}
          </div>
        </div>

        {/* Right Column: Animated Dora Orb with Live State Matrix */}
        <div className="lg:col-span-5 flex flex-col items-center justify-center relative">
          <div className="relative p-10 rounded-3xl border border-surface-border/70 bg-surface/40 backdrop-blur-xl shadow-2xl w-full max-w-md flex flex-col items-center">
            {/* Corner geometric accents */}
            <div className="absolute top-3 left-3 font-mono text-[9px] text-muted-dark">
              SYS::ORB_CORE_V1
            </div>
            <div className="absolute top-3 right-3 flex items-center gap-1.5 font-mono text-[9px] text-emerald-400">
              <span className="w-1.5 h-1.5 rounded-full bg-emerald-400 animate-pulse" />
              ONLINE
            </div>

            {/* The Orb Component */}
            <div className="my-6">
              <AnimatedOrb size={220} />
            </div>

            {/* Dynamic Companion Transcript Card */}
            <div className="w-full mt-4 p-4 rounded-xl bg-background/80 border border-surface-border font-mono text-xs">
              <div className="flex items-center justify-between text-muted text-[10px] pb-2 border-b border-surface-border mb-2.5">
                <span className="text-primary font-bold">DORA GREETING</span>
                <span>HINGLISH / ENGLISH</span>
              </div>
              <p className="text-foreground leading-relaxed">
                &ldquo;Hey! Main Dora hoon. Batao, aaj kya karna hai?&rdquo;
              </p>
              <div className="mt-3 flex items-center justify-between text-[10px] text-muted-dark">
                <span>INTENT: AUTO-DETECT</span>
                <span className="text-primary">TTS SPEED: 1.0X</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}
