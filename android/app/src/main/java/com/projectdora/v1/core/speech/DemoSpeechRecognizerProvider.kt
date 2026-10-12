package com.projectdora.v1.core.speech

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DemoSpeechRecognizerProvider(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) : SpeechRecognitionProvider {

    private val _state = MutableStateFlow<SpeechState>(SpeechState.Idle)
    override val state: StateFlow<SpeechState> = _state.asStateFlow()

    private var activeJob: Job? = null

    private val sampleUtterances = listOf(
        "Find me an AI tool for researching AI in healthcare.",
        "Dora, mujhe AI ki help se research karni hai.",
        "Help me study for an exam.",
        "Find free video editing apps.",
        "Explain a PDF."
    )
    private var utteranceIndex = 0

    override fun startListening() {
        activeJob?.cancel()
        _state.value = SpeechState.Listening

        activeJob = scope.launch {
            delay(1200)
            val fullText = sampleUtterances[utteranceIndex % sampleUtterances.size]
            utteranceIndex++

            // Stream partial words
            val words = fullText.split(" ")
            var partial = ""
            for (w in words) {
                partial += (if (partial.isEmpty()) "" else " ") + w
                _state.value = SpeechState.PartialResult(partial)
                delay(180)
            }
            delay(300)
            _state.value = SpeechState.FinalResult(fullText)
        }
    }

    override fun stopListening() {
        // Allow job to finish
    }

    override fun cancel() {
        activeJob?.cancel()
        _state.value = SpeechState.Idle
    }

    override fun isAvailable(): Boolean = true
}
