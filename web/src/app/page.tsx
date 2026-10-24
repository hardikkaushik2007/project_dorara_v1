"use client";

import { useState, useEffect } from "react";
import { Navigation } from "@/components/Navigation";
import { Hero } from "@/components/Hero";
import { ScrollytellingSection } from "@/components/ScrollytellingSection";
import { AppDiscoverySection } from "@/components/AppDiscoverySection";
import { ComparisonSection } from "@/components/ComparisonSection";
import { ArchitectureSection } from "@/components/ArchitectureSection";
import { Footer } from "@/components/Footer";
import { AppEntity } from "@/lib/utils";
import { FALLBACK_APPS } from "@/lib/data";

export default function Home() {
  const [apps, setApps] = useState<AppEntity[]>(FALLBACK_APPS);
  const [selectedForCompare, setSelectedForCompare] = useState<string[]>([
    "perplexity",
    "consensus",
  ]);
  const [activeQuery, setActiveQuery] = useState("");

  useEffect(() => {
    fetch("/api/v1/apps")
      .then((res) => {
        if (!res.ok) throw new Error("Backend offline");
        return res.json();
      })
      .then((data: AppEntity[]) => {
        if (Array.isArray(data) && data.length > 0) {
          setApps(data);
        }
      })
      .catch(() => {
        // Fallback already pre-set to verified data
      });
  }, []);

  const handleCompareToggle = (appId: string) => {
    setSelectedForCompare((prev) => {
      if (prev.includes(appId)) {
        return prev.filter((id) => id !== appId);
      } else {
        if (prev.length >= 3) {
          return [...prev.slice(1), appId];
        }
        return [...prev, appId];
      }
    });
  };

  const selectedAppEntities = apps.filter((a) =>
    selectedForCompare.includes(a.id)
  );

  return (
    <main className="min-h-screen bg-background text-foreground flex flex-col selection:bg-primary/20 selection:text-primary">
      <Navigation />
      <Hero
        apps={apps}
        onRunQuery={(query) => {
          setActiveQuery(query);
        }}
      />
      <ScrollytellingSection />
      <AppDiscoverySection
        apps={apps}
        initialQuery={activeQuery}
        selectedForCompare={selectedForCompare}
        onCompareToggle={handleCompareToggle}
      />
      <ComparisonSection
        selectedApps={selectedAppEntities}
        onClear={() => setSelectedForCompare([])}
      />
      <ArchitectureSection />
      <Footer />
    </main>
  );
}
