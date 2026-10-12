package com.projectdora.v1.data.repository

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class DoraSettings(
    val language: String = "Hinglish",
    val spokenResponseEnabled: Boolean = true,
    val speechRate: Float = 1.0f,
    val demoModeEnabled: Boolean = false,
    val backendUrl: String = "http://10.0.2.2:8000" // Android emulator loopback to host
)

class SettingsRepository(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("dora_settings", Context.MODE_PRIVATE)

    private val _settings = MutableStateFlow(
        DoraSettings(
            language = prefs.getString("language", "Hinglish") ?: "Hinglish",
            spokenResponseEnabled = prefs.getBoolean("spoken_response", true),
            speechRate = prefs.getFloat("speech_rate", 1.0f),
            demoModeEnabled = prefs.getBoolean("demo_mode", false),
            backendUrl = prefs.getString("backend_url", "http://10.0.2.2:8000") ?: "http://10.0.2.2:8000"
        )
    )
    val settings: StateFlow<DoraSettings> = _settings.asStateFlow()

    fun updateLanguage(lang: String) {
        prefs.edit().putString("language", lang).apply()
        _settings.value = _settings.value.copy(language = lang)
    }

    fun toggleSpokenResponse(enabled: Boolean) {
        prefs.edit().putBoolean("spoken_response", enabled).apply()
        _settings.value = _settings.value.copy(spokenResponseEnabled = enabled)
    }

    fun updateSpeechRate(rate: Float) {
        prefs.edit().putFloat("speech_rate", rate).apply()
        _settings.value = _settings.value.copy(speechRate = rate)
    }

    fun toggleDemoMode(enabled: Boolean) {
        prefs.edit().putBoolean("demo_mode", enabled).apply()
        _settings.value = _settings.value.copy(demoModeEnabled = enabled)
    }

    fun updateBackendUrl(url: String) {
        prefs.edit().putString("backend_url", url).apply()
        _settings.value = _settings.value.copy(backendUrl = url)
    }
}
