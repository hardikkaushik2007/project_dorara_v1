"use client";

import { AppEntity } from "@/lib/utils";
import { Check, X, ArrowUpRight, Scale } from "lucide-react";

interface ComparisonProps {
  selectedApps: AppEntity[];
  onClear: () => void;
}

export function ComparisonSection({ selectedApps, onClear }: ComparisonProps) {
  if (selectedApps.length < 2) {
    return (
      <section id="comparison" className="py-24 border-b border-surface-border">
        <div className="max-w-7xl mx-auto px-6 md:px-12 text-center py-16 rounded-3xl border border-surface-border bg-surface/30">
          <div className="inline-flex p-3 rounded-full bg-surface border border-surface-border text-primary mb-4">
            <Scale className="w-5 h-5" />
          </div>
          <h3 className="text-xl font-bold text-foreground mb-2">
            Select 2 or more tools from the catalogue above
          </h3>
          <p className="text-sm text-muted max-w-md mx-auto">
            Click &ldquo;Compare&rdquo; on any application card to inspect task suitability,
            limitations, platform support, and pricing models side by side.
          </p>
        </div>
      </section>
    );
  }

  const criteria = [
    {
      title: "Task Suitability",
      getValue: (app: AppEntity) => app.best_use_case,
    },
    {
      title: "Pricing Details",
      getValue: (app: AppEntity) => `${app.pricing_model}: ${app.pricing_details}`,
    },
    {
      title: "Free Tier Limitations",
      getValue: (app: AppEntity) => app.known_limitations.join("; "),
    },
    {
      title: "Platform Availability",
      getValue: (app: AppEntity) =>
        app.android_available ? "Native Android App + Web" : "Web Only (Mobile Browser)",
    },
    {
      title: "Automation Level",
      getValue: (app: AppEntity) =>
        app.automation_capabilities
          .map((c) => c.replace(/_/g, " ").toLowerCase())
          .join(", "),
    },
    {
      title: "Last Verified",
      getValue: (app: AppEntity) => app.last_verified_date,
    },
  ];

  return (
    <section id="comparison" className="py-24 md:py-36 border-b border-surface-border">
      <div className="max-w-7xl mx-auto px-6 md:px-12">
        {/* Header */}
        <div className="flex items-center justify-between mb-12">
          <div>
            <div className="font-mono text-xs uppercase tracking-widest text-primary mb-2">
              03 // COMPARATIVE MATRIX
            </div>
            <h2 className="text-3xl sm:text-5xl font-bold tracking-tight text-foreground">
              Side-by-side evidence analysis.
            </h2>
          </div>
          <button
            onClick={onClear}
            className="text-xs font-mono px-3 py-1.5 rounded border border-surface-border text-muted hover:text-foreground hover:bg-surface"
          >
            Clear Comparison
          </button>
        </div>

        {/* Verdict Banner */}
        <div className="p-6 rounded-2xl bg-surface-elevated border border-primary/40 mb-10 font-mono text-xs text-foreground flex items-start gap-3">
          <span className="text-primary font-bold text-sm">DORA_VERDICT:</span>
          <p className="leading-relaxed">
            Comparing {selectedApps.map((a) => a.name).join(" and ")}:{" "}
            {selectedApps[0].name} excels in {selectedApps[0].best_use_case}, whereas{" "}
            {selectedApps[1].name} is preferred for {selectedApps[1].best_use_case}.
            Choose according to whether you require native mobile execution or deeper specialized workflows.
          </p>
        </div>

        {/* Comparison Table */}
        <div className="overflow-x-auto rounded-2xl border border-surface-border bg-surface">
          <table className="w-full text-left font-mono text-xs">
            <thead>
              <tr className="border-b border-surface-border bg-surface-elevated">
                <th className="p-5 font-bold text-muted uppercase tracking-wider w-48">
                  Criterion
                </th>
                {selectedApps.map((app) => (
                  <th key={app.id} className="p-5 font-bold text-primary text-sm">
                    {app.name}
                  </th>
                ))}
              </tr>
            </thead>
            <tbody className="divide-y divide-surface-border">
              {criteria.map((crit, idx) => (
                <tr key={idx} className="hover:bg-surface-elevated/40 transition-colors">
                  <td className="p-5 text-muted font-semibold bg-surface-elevated/20">
                    {crit.title}
                  </td>
                  {selectedApps.map((app) => (
                    <td key={app.id} className="p-5 text-foreground leading-relaxed">
                      {crit.getValue(app)}
                    </td>
                  ))}
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </section>
  );
}
