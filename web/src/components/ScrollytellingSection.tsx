"use client";

import { motion } from "framer-motion";
import { useState } from "react";
import { Sparkles, Compass, ShieldCheck, CheckCircle2, ChevronRight, ArrowUpRight } from "lucide-react";

const NARRATIVE_STEPS = [
  {
    step: "01",
    tag: "VOICE INTENT EXTRACTION",
    title: "Speak in natural English or Hinglish.",
    description:
      "Say 'Dora, mujhe AI ki help se research karni hai' or 'Find me an AI tool for healthcare research.' Dora decomposes your request into domain constraints, academic rigor requirements, and platform targets.",
    preview: {
      userVoice: "Find me an AI tool for researching AI in healthcare.",
      intent: "Discover digital tools for Academic Research / Healthcare",
      category: "Academic research",
      keywords: ["healthcare", "research", "clinical", "citations"],
    },
  },
  {
    step: "02",
    tag: "EVIDENCE-BASED DISCOVERY",
    title: "Unmatched verified curation over random web search.",
    description:
      "Dora indexes verified capabilities across 20+ applications with audited pricing, Android package targets, and real-time limitation disclaimers. No fabricated ratings or hallucinated features.",
    preview: {
      candidates: [
        { name: "Perplexity AI", fit: "Broad research with direct web citations" },
        { name: "Consensus", fit: "Peer-reviewed biomedical consensus extractor" },
        { name: "Elicit", fit: "Systematic literature review & trial parameters" },
      ],
      verdict: "Consensus and Perplexity ranked top for clinical source citations.",
    },
  },
  {
    step: "03",
    tag: "CAPABILITY-DRIVEN LAUNCH",
    title: "Safe execution with honest guidance fallback.",
    description:
      "Dora launches verified HTTPS custom tabs or installed apps. When third-party apps restrict automated form submission, Dora stages the prompt, copies it to clipboard, and guides you step-by-step.",
    preview: {
      actionStatus: "VERIFIED",
      method: "Verified HTTPS Custom Tab Launch",
      stagedPrompt: "Recent clinical trials and verified applications of artificial intelligence in healthcare diagnostics.",
      guidance: "Query copied to clipboard. Paste into search bar to review citations.",
    },
  },
];

export function ScrollytellingSection() {
  const [activeStep, setActiveStep] = useState(0);

  return (
    <section id="story" className="py-24 md:py-36 border-b border-surface-border relative">
      <div className="max-w-7xl mx-auto px-6 md:px-12">
        {/* Section Header */}
        <div className="mb-16 md:mb-24 max-w-2xl">
          <div className="font-mono text-xs uppercase tracking-widest text-primary mb-3">
            01 // ARCHITECTURAL WALKTHROUGH
          </div>
          <h2 className="text-3xl sm:text-5xl font-bold tracking-tight text-foreground leading-tight">
            How Dora solves the app discovery paradox.
          </h2>
          <p className="mt-4 text-muted text-base">
            Instead of forcing users to know every tool beforehand, Dora acts as
            an informed companion who finds the right tool and helps you use it.
          </p>
        </div>

        {/* Scrollytelling Split Grid */}
        <div className="grid grid-cols-1 lg:grid-cols-12 gap-12 items-start">
          {/* Left Column: Progressive Steps */}
          <div className="lg:col-span-6 flex flex-col gap-8">
            {NARRATIVE_STEPS.map((item, index) => {
              const isSelected = activeStep === index;
              return (
                <div
                  key={index}
                  onClick={() => setActiveStep(index)}
                  className={`cursor-pointer p-6 md:p-8 rounded-2xl border transition-all duration-300 ${
                    isSelected
                      ? "bg-surface-elevated/80 border-primary/50 shadow-2xl"
                      : "bg-surface/30 border-surface-border hover:border-surface-border-subtle"
                  }`}
                >
                  <div className="flex items-center gap-3 mb-3">
                    <span className="font-mono text-xs text-primary font-bold">
                      {item.step}
                    </span>
                    <span className="text-[10px] font-mono tracking-widest uppercase text-muted">
                      {item.tag}
                    </span>
                  </div>
                  <h3 className="text-xl font-bold text-foreground mb-3">
                    {item.title}
                  </h3>
                  <p className="text-sm text-muted leading-relaxed">
                    {item.description}
                  </p>
                </div>
              );
            })}
          </div>

          {/* Right Column: Sticky Reactive Terminal / Preview Panel */}
          <div className="lg:col-span-6 lg:sticky lg:top-28">
            <div className="p-6 md:p-8 rounded-3xl border border-surface-border bg-surface-elevated shadow-2xl font-mono text-xs overflow-hidden relative">
              {/* Terminal Header */}
              <div className="flex items-center justify-between pb-4 border-b border-surface-border text-muted mb-6">
                <div className="flex items-center gap-2">
                  <div className="w-2.5 h-2.5 rounded-full bg-red-500/70" />
                  <div className="w-2.5 h-2.5 rounded-full bg-yellow-500/70" />
                  <div className="w-2.5 h-2.5 rounded-full bg-emerald-500/70" />
                  <span className="text-[11px] text-muted ml-2">dora-session.log</span>
                </div>
                <span className="text-primary text-[10px] uppercase">
                  STEP_{NARRATIVE_STEPS[activeStep].step}
                </span>
              </div>

              {/* Dynamic Content Preview based on active step */}
              {activeStep === 0 && (
                <motion.div
                  initial={{ opacity: 0, y: 10 }}
                  animate={{ opacity: 1, y: 0 }}
                  className="space-y-4"
                >
                  <div className="text-muted text-[11px]">
                    &gt; RECEIVING_AUDIO_STREAM...
                  </div>
                  <div className="p-3 rounded-lg bg-surface border border-surface-border text-primary font-semibold">
                    &ldquo;Find me an AI tool for researching AI in healthcare.&rdquo;
                  </div>
                  <div className="p-4 rounded-xl bg-background border border-surface-border space-y-2">
                    <div className="text-[10px] text-muted uppercase">Extracted Intent:</div>
                    <div className="text-foreground font-sans text-sm">
                      Discover digital tools for Academic Research / Healthcare
                    </div>
                    <div className="flex flex-wrap gap-1.5 pt-2">
                      {["healthcare", "research", "clinical", "citations"].map((kw, i) => (
                        <span key={i} className="px-2 py-0.5 rounded bg-surface border border-surface-border text-[10px] text-secondary">
                          #{kw}
                        </span>
                      ))}
                    </div>
                  </div>
                </motion.div>
              )}

              {activeStep === 1 && (
                <motion.div
                  initial={{ opacity: 0, y: 10 }}
                  animate={{ opacity: 1, y: 0 }}
                  className="space-y-4"
                >
                  <div className="text-muted text-[11px]">
                    &gt; AUDITED_REGISTRY_CANDIDATES:
                  </div>
                  {[
                    { name: "Perplexity AI", fit: "Broad research with direct web citations" },
                    { name: "Consensus", fit: "Peer-reviewed biomedical consensus extractor" },
                    { name: "Elicit", fit: "Systematic literature review & trial parameters" },
                  ].map((cand, i) => (
                    <div key={i} className="p-3 rounded-lg bg-surface border border-surface-border flex items-center justify-between">
                      <span className="font-bold text-foreground">{cand.name}</span>
                      <span className="text-[11px] text-muted">{cand.fit}</span>
                    </div>
                  ))}
                  <div className="p-3 rounded-lg bg-primary/10 border border-primary/30 text-primary text-[11px]">
                    💡 Consensus and Perplexity ranked top for clinical source citations.
                  </div>
                </motion.div>
              )}

              {activeStep === 2 && (
                <motion.div
                  initial={{ opacity: 0, y: 10 }}
                  animate={{ opacity: 1, y: 0 }}
                  className="space-y-4"
                >
                  <div className="flex items-center justify-between">
                    <span className="text-muted text-[11px]">&gt; ACTION_STATE:</span>
                    <span className="px-2 py-0.5 rounded bg-emerald-500/20 text-emerald-400 font-bold text-[10px] border border-emerald-500/40">
                      STATUS_VERIFIED
                    </span>
                  </div>
                  <div className="p-3 rounded-lg bg-surface border border-surface-border text-foreground">
                    Method: Verified HTTPS Custom Tab Launch
                  </div>
                  <div className="p-3 rounded-lg bg-background border border-surface-border">
                    <div className="text-muted text-[10px] uppercase mb-1">Staged Clipboard Prompt:</div>
                    <div className="text-primary text-[11px]">
                      &ldquo;Recent clinical trials and verified applications of artificial intelligence in healthcare diagnostics.&rdquo;
                    </div>
                  </div>
                  <div className="p-2.5 rounded text-[11px] text-muted-dark border border-surface-border/50">
                    ℹ️ Query copied to clipboard. Paste into search bar to review citations.
                  </div>
                </motion.div>
              )}
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}
