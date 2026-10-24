"use client";

import { useState, useEffect, useRef } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { AnimatedOrb } from "./AnimatedOrb";
import { Mic, MicOff, ArrowRight, Volume2, VolumeX, Sparkles, Terminal, CheckCircle2, AlertCircle } from "lucide-react";
import { AppEntity } from "@/lib/utils";

interface HeroProps {
  onRunQuery: (query: string) => void;
  apps: AppEntity[];
}

export function Hero({ onRunQuery, apps }: HeroProps) {
  const [inputValue, setInputValue] = useState("");
  const [isListening, setIsListening] = useState(false);
  const [isSpeaking, setIsSpeaking] = useState(false);
  const [ttsEnabled, setTtsEnabled] = useState(true);
  const [assistantMessage, setAssistantMessage] = useState(
    "Hey! Main Dora hoon. Batao, aaj kya karna hai?"
  );
  const [orbState, setOrbState] = useState<"idle" | "listening" | "thinking" | "speaking">("idle");
  const [speechSupported, setSpeechSupported] = useState(true);
  const [liveTranscript, setLiveTranscript] = useState("");

  const recognitionRef = useRef<any>(null);

  const samplePrompts = [
    "Find me an AI tool for researching AI in healthcare.",
    "Dora, mujhe AI ki help se research karni hai.",
    "Find free video editing apps for short-form content.",
    "Help me study for an upcoming medical exam.",
    "I need a tool to chat with PDF research papers.",
  ];

  // Initialize Speech Recognition
  useEffect(() => {
    if (typeof window !== "undefined") {
      const SpeechRecognition =
        (window as any).SpeechRecognition || (window as any).webkitSpeechRecognition;

      if (SpeechRecognition) {
        const recognition = new SpeechRecognition();
        recognition.continuous = false;
        recognition.interimResults = true;
        recognition.lang = "en-IN"; // English/Hinglish Indian locale

        recognition.onstart = () => {
          setIsListening(true);
          setOrbState("listening");
          setLiveTranscript("Listening to you...");
        };

        recognition.onresult = (event: any) => {
          let interim = "";
          let final = "";
          for (let i = event.resultIndex; i < event.results.length; ++i) {
            if (event.results[i].isFinal) {
              final += event.results[i][0].transcript;
            } else {
              interim += event.results[i][0].transcript;
            }
          }
          const current = final || interim;
          setInputValue(current);
          setLiveTranscript(current);

          if (final) {
            handleProcessQuery(final);
          }
        };

        recognition.onerror = (event: any) => {
          console.warn("Speech recognition error:", event.error);
          setIsListening(false);
          setOrbState("idle");
          setLiveTranscript("");
        };

        recognition.onend = () => {
          setIsListening(false);
        };

        recognitionRef.current = recognition;
      } else {
        setSpeechSupported(false);
      }
    }

    return () => {
      if (typeof window !== "undefined" && window.speechSynthesis) {
        window.speechSynthesis.cancel();
      }
    };
  }, [apps, ttsEnabled]);

  // Voice synthesis (Text to Speech)
  const speakText = (text: string) => {
    if (!ttsEnabled || typeof window === "undefined" || !("speechSynthesis" in window)) {
      return;
    }
    window.speechSynthesis.cancel();

    const utterance = new SpeechSynthesisUtterance(text);
    utterance.rate = 1.0;
    utterance.pitch = 1.05;

    // Pick a natural voice if available
    const voices = window.speechSynthesis.getVoices();
    const preferredVoice =
      voices.find((v) => v.lang.includes("en-IN") || v.lang.includes("hi-IN")) ||
      voices.find((v) => v.lang.includes("en-US"));
    if (preferredVoice) {
      utterance.voice = preferredVoice;
    }

    utterance.onstart = () => {
      setIsSpeaking(true);
      setOrbState("speaking");
    };

    utterance.onend = () => {
      setIsSpeaking(false);
      setOrbState("idle");
    };

    utterance.onerror = () => {
      setIsSpeaking(false);
      setOrbState("idle");
    };

    window.speechSynthesis.speak(utterance);
  };

  const toggleMic = () => {
    if (!speechSupported) {
      alert("Your browser does not support Speech Recognition. Please use Chrome or Edge, or type your request.");
      return;
    }

    if (isListening) {
      recognitionRef.current?.stop();
      setIsListening(false);
      setOrbState("idle");
    } else {
      if (typeof window !== "undefined" && window.speechSynthesis) {
        window.speechSynthesis.cancel();
      }
      setIsSpeaking(false);
      try {
        recognitionRef.current?.start();
      } catch (err) {
        console.error("Could not start recognition:", err);
      }
    }
  };

  const handleProcessQuery = (query: string) => {
    if (!query.trim()) return;

    setOrbState("thinking");
    onRunQuery(query);

    // Analyze intent client-side or generate natural companion response
    const qLower = query.toLowerCase();
    let responseText = "";

    if (qLower.includes("healthcare") || qLower.includes("health") || qLower.includes("medicine")) {
      responseText =
        "Healthcare aur clinical research ke liye maine Perplexity AI aur Consensus shortlist kiye hain. Consensus peer-reviewed papers ke liye best hai.";
    } else if (qLower.includes("video") || qLower.includes("edit")) {
      responseText =
        "Video creation ke liye CapCut aur Canva top recommendation hain. Dono beginner friendly aur fast hain.";
    } else if (qLower.includes("pdf") || qLower.includes("document")) {
      responseText =
        "PDF analysis ke liye ChatPDF aur Claude sabse strong options hain.";
    } else if (qLower.includes("code") || qLower.includes("program") || qLower.includes("website")) {
      responseText =
        "Coding aur app development ke liye Phind aur Replit sabse suitable hain.";
    } else if (qLower.includes("study") || qLower.includes("exam")) {
      responseText =
        "Exam preparation ke liye Quizlet aur Khan Academy recommend kiye gaye hain.";
    } else {
      responseText = `Mujhe aapki request ke liye ${apps.length} verified digital tools mile hain. Niche cards me details check kijiye.`;
    }

    setAssistantMessage(responseText);
    speakText(responseText);

    // Smooth scroll to results
    setTimeout(() => {
      const el = document.getElementById("discovery");
      el?.scrollIntoView({ behavior: "smooth" });
    }, 400);
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (inputValue.trim()) {
      handleProcessQuery(inputValue);
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
              Project Dora V1 — Voice First
            </span>
          </div>

          {/* Editorial Display Headline */}
          <h1 className="text-4xl sm:text-6xl lg:text-7xl font-bold tracking-tight text-foreground leading-[1.05] mb-6">
            Tell Dora what you want to accomplish.
          </h1>

          <p className="text-lg md:text-xl text-muted font-normal leading-relaxed max-w-2xl mb-10">
            A futuristic, voice-first companion. Tap the microphone, speak in natural Hindi or English, and Dora will find, compare, and explain the right digital tools.
          </p>

          {/* Prompt Input Form */}
          <form
            onSubmit={handleSubmit}
            className="p-1.5 rounded-xl border border-surface-border bg-surface/90 backdrop-blur-md flex items-center gap-2 shadow-2xl focus-within:border-primary/60 transition-all max-w-2xl"
          >
            {/* Live Microphone Toggle Button */}
            <button
              type="button"
              onClick={toggleMic}
              title={isListening ? "Listening... click to stop" : "Click to speak"}
              className={`p-2.5 rounded-lg border transition-all flex items-center justify-center ${
                isListening
                  ? "bg-red-500/20 border-red-500 text-red-400 animate-pulse"
                  : "bg-surface-elevated border-surface-border text-primary hover:border-primary/50"
              }`}
            >
              {isListening ? <MicOff className="w-4 h-4" /> : <Mic className="w-4 h-4" />}
            </button>

            <input
              type="text"
              value={inputValue}
              onChange={(e) => setInputValue(e.target.value)}
              placeholder={
                isListening
                  ? "Listening to your voice..."
                  : "Speak or type: 'Find me an AI tool for researching healthcare...'"
              }
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

          {/* Live Transcript / Speech Indicator */}
          {isListening && (
            <div className="mt-3 flex items-center gap-2 text-xs font-mono text-primary animate-pulse">
              <span className="w-2 h-2 rounded-full bg-primary" />
              <span>LIVE TRANSCRIPT: {liveTranscript || "Listening..."}</span>
            </div>
          )}

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
                  handleProcessQuery(prompt);
                }}
                className="text-xs font-mono px-2.5 py-1 rounded border border-surface-border bg-surface-elevated/50 text-muted hover:text-foreground hover:border-primary/40 transition-colors truncate max-w-[280px]"
              >
                {prompt}
              </button>
            ))}
          </div>
        </div>

        {/* Right Column: Animated Dora Orb with Live Voice State */}
        <div className="lg:col-span-5 flex flex-col items-center justify-center relative">
          <div className="relative p-10 rounded-3xl border border-surface-border/70 bg-surface/40 backdrop-blur-xl shadow-2xl w-full max-w-md flex flex-col items-center">
            {/* Corner geometric accents */}
            <div className="absolute top-3 left-3 font-mono text-[9px] text-muted-dark">
              SYS::VOICE_ORB
            </div>
            <div className="absolute top-3 right-3 flex items-center gap-2 font-mono text-[9px]">
              {/* Spoken voice toggle */}
              <button
                type="button"
                onClick={() => {
                  setTtsEnabled(!ttsEnabled);
                  if (isSpeaking) {
                    window.speechSynthesis?.cancel();
                    setIsSpeaking(false);
                    setOrbState("idle");
                  }
                }}
                title={ttsEnabled ? "Voice Output ON" : "Voice Output MUTED"}
                className="flex items-center gap-1 text-muted hover:text-foreground transition-colors"
              >
                {ttsEnabled ? (
                  <>
                    <Volume2 className="w-3 h-3 text-primary" />
                    <span className="text-primary">VOICE ON</span>
                  </>
                ) : (
                  <>
                    <VolumeX className="w-3 h-3 text-muted" />
                    <span>MUTED</span>
                  </>
                )}
              </button>
            </div>

            {/* Clickable Interactive Orb */}
            <div
              className="my-6 cursor-pointer group"
              onClick={toggleMic}
              title="Click Orb to start speaking"
            >
              <AnimatedOrb state={orbState} size={220} />
            </div>

            <div className="text-[10px] font-mono text-muted-dark -mt-2 mb-4 text-center">
              {isListening ? (
                <span className="text-red-400 font-bold">● LISTENING NOW... CLICK TO STOP</span>
              ) : isSpeaking ? (
                <span className="text-primary font-bold">▶ DORA IS SPEAKING...</span>
              ) : (
                <span>CLICK ORB OR MIC TO SPEAK</span>
              )}
            </div>

            {/* Dynamic Companion Transcript Card */}
            <div className="w-full p-4 rounded-xl bg-background/80 border border-surface-border font-mono text-xs">
              <div className="flex items-center justify-between text-muted text-[10px] pb-2 border-b border-surface-border mb-2.5">
                <span className="text-primary font-bold">DORA SPOKEN RESPONSE</span>
                <span>{isSpeaking ? "SPEAKING" : "IDLE"}</span>
              </div>
              <p className="text-foreground leading-relaxed">
                &ldquo;{assistantMessage}&rdquo;
              </p>
              <div className="mt-3 flex items-center justify-between text-[10px] text-muted-dark">
                <span>INTENT ENGINE: ACTIVE</span>
                <button
                  type="button"
                  onClick={() => speakText(assistantMessage)}
                  className="text-primary hover:underline"
                >
                  Replay Audio
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}
