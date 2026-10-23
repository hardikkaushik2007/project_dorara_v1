"use client";

import { useState } from "react";
import { AppEntity } from "@/lib/utils";
import { ExternalLink, Check, Copy, AlertCircle, Smartphone, Globe } from "lucide-react";

interface DiscoveryProps {
  apps: AppEntity[];
  initialQuery?: string;
  onCompareToggle: (appId: string) => void;
  selectedForCompare: string[];
}

export function AppDiscoverySection({
  apps,
  initialQuery = "",
  onCompareToggle,
  selectedForCompare,
}: DiscoveryProps) {
  const [selectedCategory, setSelectedCategory] = useState<string>("ALL");
  const [searchFilter, setSearchFilter] = useState<string>("");

  const categories = ["ALL", ...Array.from(new Set(apps.map((a) => a.category)))];

  const filtered = apps.filter((app) => {
    const matchesCat = selectedCategory === "ALL" || app.category === selectedCategory;
    const matchesSearch =
      !searchFilter.trim() ||
      app.name.toLowerCase().includes(searchFilter.toLowerCase()) ||
      app.description.toLowerCase().includes(searchFilter.toLowerCase()) ||
      app.best_use_case.toLowerCase().includes(searchFilter.toLowerCase()) ||
      app.tags.some((t) => t.toLowerCase().includes(searchFilter.toLowerCase()));
    return matchesCat && matchesSearch;
  });

  return (
    <section id="discovery" className="py-24 md:py-36 border-b border-surface-border">
      <div className="max-w-7xl mx-auto px-6 md:px-12">
        {/* Section Header */}
        <div className="flex flex-col md:flex-row md:items-end justify-between mb-12 gap-6">
          <div className="max-w-2xl">
            <div className="font-mono text-xs uppercase tracking-widest text-primary mb-3">
              02 // CURATED APPLICATION REGISTRY
            </div>
            <h2 className="text-3xl sm:text-5xl font-bold tracking-tight text-foreground leading-tight">
              Verified digital tools with zero fabrication.
            </h2>
            <p className="mt-4 text-muted text-base">
              Explore 20+ verified applications across 8 categories with honest
              pricing evidence, Android availability, and known limitations.
            </p>
          </div>

          {/* Search bar inside section */}
          <div className="w-full md:w-72">
            <input
              type="text"
              placeholder="Search tools, tags, pricing..."
              value={searchFilter}
              onChange={(e) => setSearchFilter(e.target.value)}
              className="w-full px-4 py-2.5 rounded-xl border border-surface-border bg-surface text-xs font-mono text-foreground placeholder:text-muted/60 focus:outline-none focus:border-primary/50"
            />
          </div>
        </div>

        {/* Category Filter Pills */}
        <div className="flex flex-wrap gap-2 mb-10">
          {categories.map((cat) => {
            const isSelected = selectedCategory === cat;
            return (
              <button
                key={cat}
                onClick={() => setSelectedCategory(cat)}
                className={`text-xs font-mono px-3.5 py-1.5 rounded-lg border transition-all ${
                  isSelected
                    ? "bg-foreground text-background font-semibold border-foreground"
                    : "bg-surface border-surface-border text-muted hover:text-foreground"
                }`}
              >
                {cat}
              </button>
            );
          })}
        </div>

        {/* Application Cards Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {filtered.map((app) => {
            const isComparing = selectedForCompare.includes(app.id);
            return (
              <div
                key={app.id}
                className={`p-6 rounded-2xl border transition-all duration-300 flex flex-col justify-between ${
                  isComparing
                    ? "bg-surface-elevated border-primary/60 shadow-xl"
                    : "bg-surface/60 border-surface-border hover:border-surface-border-subtle hover:bg-surface-elevated/40"
                }`}
              >
                <div>
                  {/* Top Bar: Icon Initials, Title, Platform */}
                  <div className="flex items-center justify-between mb-4">
                    <div className="flex items-center gap-3">
                      <div className="w-9 h-9 rounded-lg bg-surface border border-surface-border flex items-center justify-center font-mono text-xs font-bold text-primary">
                        {app.name.slice(0, 2).toUpperCase()}
                      </div>
                      <div>
                        <h3 className="font-bold text-foreground text-base tracking-tight">
                          {app.name}
                        </h3>
                        <span className="text-[11px] font-mono text-muted">
                          {app.category}
                        </span>
                      </div>
                    </div>
                    <div className="flex items-center gap-1 px-2 py-0.5 rounded bg-surface border border-surface-border text-[10px] font-mono text-muted">
                      {app.android_available ? (
                        <>
                          <Smartphone className="w-3 h-3 text-emerald-400" />
                          <span>ANDROID</span>
                        </>
                      ) : (
                        <>
                          <Globe className="w-3 h-3 text-secondary" />
                          <span>WEB</span>
                        </>
                      )}
                    </div>
                  </div>

                  {/* Description */}
                  <p className="text-xs text-muted leading-relaxed mb-4">
                    {app.description}
                  </p>

                  {/* Best For Box */}
                  <div className="p-3 rounded-lg bg-surface border border-surface-border mb-4 font-mono text-[11px]">
                    <div className="text-primary text-[10px] font-bold uppercase mb-1">
                      Best Use Case
                    </div>
                    <div className="text-foreground leading-snug">
                      {app.best_use_case}
                    </div>
                  </div>

                  {/* Limitations Warning */}
                  {app.known_limitations.length > 0 && (
                    <div className="flex items-start gap-1.5 text-[11px] text-amber-400/90 mb-4">
                      <AlertCircle className="w-3.5 h-3.5 shrink-0 mt-0.5" />
                      <span className="leading-tight">
                        {app.known_limitations[0]}
                      </span>
                    </div>
                  )}
                </div>

                {/* Footer Controls: Pricing & Actions */}
                <div className="pt-4 border-t border-surface-border flex items-center justify-between gap-3">
                  <div className="text-[11px] font-mono">
                    <span className="text-primary font-semibold">
                      {app.pricing_model}
                    </span>
                  </div>

                  <div className="flex items-center gap-2">
                    <button
                      onClick={() => onCompareToggle(app.id)}
                      className={`text-xs font-mono px-2.5 py-1.5 rounded border transition-colors ${
                        isComparing
                          ? "bg-primary/20 border-primary text-primary font-bold"
                          : "border-surface-border text-muted hover:text-foreground"
                      }`}
                    >
                      {isComparing ? "Selected" : "Compare"}
                    </button>
                    <a
                      href={app.official_url}
                      target="_blank"
                      rel="noreferrer"
                      className="p-1.5 rounded border border-surface-border bg-surface hover:bg-foreground hover:text-background text-muted transition-colors"
                      title="Open Official Website"
                    >
                      <ExternalLink className="w-3.5 h-3.5" />
                    </a>
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      </div>
    </section>
  );
}
