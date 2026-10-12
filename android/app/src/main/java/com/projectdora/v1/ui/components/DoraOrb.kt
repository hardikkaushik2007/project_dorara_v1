package com.projectdora.v1.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.projectdora.v1.core.model.OrbState
import com.projectdora.v1.ui.theme.CyanGlow
import com.projectdora.v1.ui.theme.CyberBlue
import com.projectdora.v1.ui.theme.ElectricCyan
import com.projectdora.v1.ui.theme.HologramPurple
import com.projectdora.v1.ui.theme.StatusAttempted
import com.projectdora.v1.ui.theme.StatusFailed
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun DoraOrb(
    state: OrbState,
    modifier: Modifier = Modifier,
    size: Dp = 180.dp,
    onClick: (() -> Unit)? = null
) {
    val transition = rememberInfiniteTransition(label = "DoraOrbTransition")

    // Pulse animation (breathing effect)
    val pulseScale by transition.animateFloat(
        initialValue = 0.92f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = when (state) {
                    OrbState.LISTENING -> 800
                    OrbState.THINKING -> 600
                    OrbState.SPEAKING -> 700
                    OrbState.EXECUTING -> 900
                    OrbState.ERROR_RECOVERY -> 1200
                    else -> 1800
                },
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "OrbPulse"
    )

    // Continuous rotation angle for orbital particles
    val rotationAngle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = when (state) {
                    OrbState.THINKING -> 2000
                    OrbState.EXECUTING -> 3000
                    else -> 8000
                },
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "OrbRotation"
    )

    // Wave modulation for speaking/listening
    val waveOffset by transition.animateFloat(
        initialValue = 0f,
        targetValue = 6.283f, // 2*PI
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "OrbWave"
    )

    val primaryColor = when (state) {
        OrbState.IDLE -> ElectricCyan
        OrbState.LISTENING -> CyberBlue
        OrbState.THINKING -> HologramPurple
        OrbState.PRESENTING -> ElectricCyan
        OrbState.EXECUTING -> CyberBlue
        OrbState.SPEAKING -> ElectricCyan
        OrbState.ERROR_RECOVERY -> StatusAttempted
    }

    val secondaryColor = when (state) {
        OrbState.IDLE -> CyberBlue
        OrbState.LISTENING -> ElectricCyan
        OrbState.THINKING -> ElectricCyan
        OrbState.PRESENTING -> HologramPurple
        OrbState.EXECUTING -> ElectricCyan
        OrbState.SPEAKING -> HologramPurple
        OrbState.ERROR_RECOVERY -> StatusFailed
    }

    Box(
        modifier = modifier
            .size(size)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val center = Offset(size.toPx() / 2f, size.toPx() / 2f)
            val baseRadius = (size.toPx() / 2.6f) * pulseScale

            // 1. Soft Outer Glow Aura
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(primaryColor.copy(alpha = 0.35f), Color.Transparent),
                    center = center,
                    radius = baseRadius * 1.5f
                ),
                radius = baseRadius * 1.5f,
                center = center
            )

            // 2. Concentric Ring or Orbitals
            val ringStrokeWidth = 3.dp.toPx()
            when (state) {
                OrbState.LISTENING, OrbState.SPEAKING -> {
                    // Expanding dynamic ripples
                    for (i in 1..3) {
                        val rippleRadius = baseRadius * (1f + (i * 0.14f * pulseScale))
                        drawCircle(
                            color = primaryColor.copy(alpha = 0.4f / i),
                            radius = rippleRadius,
                            center = center,
                            style = Stroke(width = ringStrokeWidth)
                        )
                    }
                }
                OrbState.THINKING, OrbState.EXECUTING -> {
                    // Multi-orbit rotating gyroscopic rings
                    val rad = Math.toRadians(rotationAngle.toDouble())
                    val ringX = (cos(rad) * baseRadius * 0.25f).toFloat()
                    val ringY = (sin(rad) * baseRadius * 0.25f).toFloat()
                    drawCircle(
                        color = secondaryColor.copy(alpha = 0.6f),
                        radius = baseRadius * 1.2f,
                        center = center + Offset(ringX, ringY),
                        style = Stroke(width = ringStrokeWidth)
                    )
                }
                else -> {
                    // Calm outer boundary ring
                    drawCircle(
                        color = primaryColor.copy(alpha = 0.25f),
                        radius = baseRadius * 1.15f,
                        center = center,
                        style = Stroke(width = 1.5.dp.toPx())
                    )
                }
            }

            // 3. Central Energy Core (Gradient Sphere)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.9f),
                        primaryColor,
                        secondaryColor,
                        NavyBackground.copy(alpha = 0.8f)
                    ),
                    center = center - Offset(baseRadius * 0.2f, baseRadius * 0.2f),
                    radius = baseRadius
                ),
                radius = baseRadius,
                center = center
            )

            // 4. Harmonic Speech Wave Overlay (when speaking or listening)
            if (state == OrbState.SPEAKING || state == OrbState.LISTENING) {
                val waveCount = 5
                val barWidth = 4.dp.toPx()
                val spacing = 10.dp.toPx()
                val startX = center.x - ((waveCount - 1) * spacing) / 2f

                for (i in 0 until waveCount) {
                    val waveHeight = (baseRadius * 0.45f) * (0.4f + 0.6f * sin(waveOffset + i * 1.2).toFloat().coerceIn(0.1f, 1.0f))
                    val bx = startX + (i * spacing)
                    drawLine(
                        color = Color.White.copy(alpha = 0.85f),
                        start = Offset(bx, center.y - waveHeight / 2f),
                        end = Offset(bx, center.y + waveHeight / 2f),
                        strokeWidth = barWidth
                    )
                }
            } else {
                // Futuristic central core pinpoint
                drawCircle(
                    color = Color.White.copy(alpha = 0.95f),
                    radius = 5.dp.toPx() * pulseScale,
                    center = center
                )
            }
        }
    }
}
