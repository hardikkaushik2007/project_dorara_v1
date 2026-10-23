import type { Metadata } from "next";
import { Inter, JetBrains_Mono } from "next/font/google";
import "./globals.css";

const inter = Inter({
  subsets: ["latin"],
  variable: "--font-geist-sans",
  display: "swap",
});

const jetbrainsMono = JetBrains_Mono({
  subsets: ["latin"],
  variable: "--font-geist-mono",
  display: "swap",
});

export const metadata: Metadata = {
  title: "Project Dora — The Voice-First AI Companion for App Discovery",
  description:
    "Tell Dora what you want to accomplish. Dora discovers, compares, and safely guides you through the right digital tools.",
  openGraph: {
    title: "Project Dora V1",
    description: "Futuristic Voice-First AI Companion for App Discovery and Guided Automation",
    url: "https://project-dorara-v1.vercel.app",
    siteName: "Project Dora",
    type: "website",
  },
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" className="dark">
      <body className={`${inter.variable} ${jetbrainsMono.variable} font-sans bg-background text-foreground antialiased selection:bg-primary/20 selection:text-primary`}>
        <div className="fixed inset-0 pointer-events-none noise-overlay z-50 opacity-40" />
        <div className="fixed inset-0 pointer-events-none bg-grid-pattern opacity-60 z-0" />
        <div className="relative z-10">{children}</div>
      </body>
    </html>
  );
}
