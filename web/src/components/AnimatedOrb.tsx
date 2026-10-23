"use client";

import { motion } from "framer-motion";

interface OrbProps {
  state?: "idle" | "listening" | "thinking" | "speaking" | "executing";
  size?: number;
}

export function AnimatedOrb({ state = "idle", size = 260 }: OrbProps) {
  return (
    <div
      className="relative flex items-center justify-center select-none"
      style={{ width: size, height: size }}
    >
      {/* Outer Glow Halo */}
      <motion.div
        animate={{
          scale: [0.95, 1.08, 0.95],
          opacity: [0.25, 0.45, 0.25],
        }}
        transition={{
          duration: 4,
          repeat: Infinity,
          ease: "easeInOut",
        }}
        className="absolute inset-0 rounded-full blur-2xl"
        style={{
          background: "radial-gradient(circle, #00E5FF 0%, #6366F1 50%, transparent 75%)",
        }}
      />

      {/* Orbiting Gyro Ring 1 */}
      <motion.div
        animate={{ rotate: 360 }}
        transition={{ duration: 18, repeat: Infinity, ease: "linear" }}
        className="absolute inset-3 rounded-full border border-primary/20 border-dashed"
      />

      {/* Orbiting Gyro Ring 2 */}
      <motion.div
        animate={{ rotate: -360 }}
        transition={{ duration: 12, repeat: Infinity, ease: "linear" }}
        className="absolute inset-7 rounded-full border border-secondary/25"
        style={{ transform: "rotateX(60deg)" }}
      />

      {/* Core Plasma Sphere */}
      <motion.div
        animate={{
          scale: [0.96, 1.03, 0.96],
        }}
        transition={{
          duration: 3,
          repeat: Infinity,
          ease: "easeInOut",
        }}
        className="relative w-28 h-28 rounded-full overflow-hidden shadow-2xl flex items-center justify-center border border-white/20"
        style={{
          background:
            "radial-gradient(circle at 35% 35%, #ffffff 0%, #00E5FF 35%, #1D4ED8 70%, #05070D 100%)",
          boxShadow: "0 0 40px rgba(0, 229, 255, 0.4)",
        }}
      >
        {/* Core Pulsing Point */}
        <div className="w-2.5 h-2.5 rounded-full bg-white shadow-[0_0_12px_#fff]" />
      </motion.div>

      {/* Soundwave modulation bars */}
      <div className="absolute flex items-center gap-1.5 z-20">
        {[0.4, 0.8, 1, 0.7, 0.3].map((height, i) => (
          <motion.div
            key={i}
            animate={{
              scaleY: [height * 0.4, height * 1.3, height * 0.4],
            }}
            transition={{
              duration: 1.2,
              repeat: Infinity,
              ease: "easeInOut",
              delay: i * 0.15,
            }}
            className="w-1 bg-white/90 rounded-full h-8"
          />
        ))}
      </div>
    </div>
  );
}
