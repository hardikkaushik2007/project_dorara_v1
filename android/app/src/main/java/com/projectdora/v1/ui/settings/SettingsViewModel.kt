package com.projectdora.v1.ui.settings

import androidx.lifecycle.ViewModel
import com.projectdora.v1.data.repository.DoraSettings
import com.projectdora.v1.data.repository.SettingsRepository
import com.projectdora.v1.data.repository.TaskHistoryRepository
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
    private val taskHistoryRepository: TaskHistoryRepository
) : ViewModel() {

    val settings: StateFlow<DoraSettings> = settingsRepository.settings

    fun setLanguage(lang: String) {
        settingsRepository.updateLanguage(lang)
    }

    fun toggleSpokenResponse(enabled: Boolean) {
        settingsRepository.toggleSpokenResponse(enabled)
    }

    fun setSpeechRate(rate: Float) {
        settingsRepository.updateSpeechRate(rate)
    }

    fun toggleDemoMode(enabled: Boolean) {
        settingsRepository.toggleDemoMode(enabled)
    }

    fun updateBackendUrl(url: String) {
        settingsRepository.updateBackendUrl(url)
    }

    fun clearAllData() {
        taskHistoryRepository.clearHistory()
    }
}
