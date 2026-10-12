package com.projectdora.v1.core.speech

import kotlinx.coroutines.flow.StateFlow

interface TextToSpeechProvider {
    val isSpeaking: StateFlow<Boolean>
    fun speak(text: String, onComplete: (() -> Unit)? = null)
    fun stop()
    fun setSpeechRate(rate: Float)
    fun isAvailable(): Boolean
    fun shutdown()
}
