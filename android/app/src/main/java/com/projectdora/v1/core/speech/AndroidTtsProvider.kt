package com.projectdora.v1.core.speech

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale
import java.util.UUID

class AndroidTtsProvider(
    context: Context
) : TextToSpeechProvider {

    private val _isSpeaking = MutableStateFlow(false)
    override val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private var tts: TextToSpeech? = null
    private var isInitialized = false
    private var pendingSpeech: Pair<String, (() -> Unit)?>? = null
    private var currentCallback: (() -> Unit)? = null

    init {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                isInitialized = true
                val result = tts?.setLanguage(Locale("en", "IN"))
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    tts?.language = Locale.US
                }
                tts?.setSpeechRate(1.0f)
                tts?.setPitch(1.05f)

                tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {
                        _isSpeaking.value = true
                    }

                    override fun onDone(utteranceId: String?) {
                        _isSpeaking.value = false
                        currentCallback?.invoke()
                        currentCallback = null
                    }

                    override fun onError(utteranceId: String?) {
                        _isSpeaking.value = false
                        currentCallback = null
                    }
                })

                pendingSpeech?.let { (text, callback) ->
                    speak(text, callback)
                    pendingSpeech = null
                }
            }
        }
    }

    override fun speak(text: String, onComplete: (() -> Unit)?) {
        if (!isInitialized) {
            pendingSpeech = Pair(text, onComplete)
            return
        }
        currentCallback = onComplete
        _isSpeaking.value = true
        val utteranceId = UUID.randomUUID().toString()
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
    }

    override fun stop() {
        tts?.stop()
        _isSpeaking.value = false
        currentCallback = null
    }

    override fun setSpeechRate(rate: Float) {
        tts?.setSpeechRate(rate)
    }

    override fun isAvailable(): Boolean = isInitialized

    override fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}
