"use client";

import { Shield, Cpu, Lock, CheckCircle, RefreshCw } from "lucide-react";

export function ArchitectureSection() {
  const capabilities = [
    {
      title: "Honest State Verification",
      code: "REQUESTED -> ATTEMPTED -> COMPLETED -> VERIFIED",
      description:
        "Dora refuses to claim an action has succeeded until observable criteria confirm it. Launching a browser window does not pretend the user's research is finished.",
    },
    {
      title: "Zero Keystroke Injection Abuse",
      code: "PERMISSION_MODEL: EXPLICIT_CONSENT",
      description:
        "No intrusive AccessibilityService permission requests during onboarding. Dora formats and stages your query, then guides you with zero background tracking.",
    },
    {
      title: "Dual Operation Mode",
      code: "OFFLINE_FIRST + CLOUD_ENHANCED",
      description:
        "Works 100% offline with an embedded Android catalogue and on-device NLP. When connected to the cloud, it accesses real-time ranking and index synchronization.",
    },
  ];

  return (
    <section id="architecture" className="py-24 md:py-36 border-b border-surface-border">
      <div className="max-w-7xl mx-auto px-6 md:px-12">
        <div className="max-w-2xl mb-16">
          <div className="font-mono text-xs uppercase tracking-widest text-primary mb-3">
            04 // CAPABILITY & TRANSPARENCY
          </div>
          <h2 className="text-3xl sm:text-5xl font-bold tracking-tight text-foreground leading-tight">
            Built on technical honesty.
          </h2>
          <p className="mt-4 text-muted text-base">
            No universal automation illusions. A disciplined capability matrix
            designed to keep users informed and in control.
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          {capabilities.map((cap, i) => (
            <div
              key={i}
              className="p-8 rounded-3xl border border-surface-border bg-surface flex flex-col justify-between"
            >
              <div>
                <div className="font-mono text-[10px] text-primary tracking-widest uppercase mb-3">
                  // CAPABILITY_{i + 1}
                </div>
                <h3 className="text-xl font-bold text-foreground mb-4">
                  {cap.title}
                </h3>
                <div className="p-3 rounded-lg bg-surface-elevated border border-surface-border font-mono text-[11px] text-secondary mb-4 break-all">
                  {cap.code}
                </div>
                <p className="text-xs text-muted leading-relaxed">
                  {cap.description}
                </p>
              </div>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}
