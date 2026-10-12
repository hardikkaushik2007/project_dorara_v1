package com.projectdora.v1.core.speech

import kotlinx.coroutines.flow.StateFlow

sealed class SpeechState {
    object Idle : SpeechState()
    object Listening : SpeechState()
    data class PartialResult(val text: String) : SpeechState()
    data class FinalResult(val text: String) : SpeechState()
    data class Error(val message: String) : SpeechState()
}

interface SpeechRecognitionProvider {
    val state: StateFlow<SpeechState>
    fun startListening()
    fun stopListening()
    fun cancel()
    fun isAvailable(): Boolean
}
