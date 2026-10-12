package com.projectdora.v1.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectdora.v1.core.model.AppEntity
import com.projectdora.v1.core.model.OrbState
import com.projectdora.v1.core.model.TaskSession
import com.projectdora.v1.core.speech.SpeechRecognitionProvider
import com.projectdora.v1.core.speech.SpeechState
import com.projectdora.v1.core.speech.TextToSpeechProvider
import com.projectdora.v1.data.repository.AppRegistryRepository
import com.projectdora.v1.data.repository.SettingsRepository
import com.projectdora.v1.data.repository.TaskHistoryRepository
import com.projectdora.v1.domain.RecommendationEngine
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class HomeNavigationEvent {
    data class NavigateToDiscover(val query: String) : HomeNavigationEvent()
    data class NavigateToWorkspace(val appId: String, val query: String) : HomeNavigationEvent()
}

data class HomeUiState(
    val orbState: OrbState = OrbState.IDLE,
    val greeting: String = "Hey! Main Dora hoon. Batao, aaj kya karna hai?",
    val liveTranscript: String = "",
    val isListening: Boolean = false,
    val suggestedChips: List<String> = listOf(
        "Find me an AI tool for researching AI in healthcare.",
        "Dora, mujhe AI ki help se research karni hai.",
        "Help me study for an exam.",
        "Find free video editing apps.",
        "Explain a PDF.",
        "Help me build a website."
    ),
    val recentApps: List<AppEntity> = emptyList(),
    val recentTasks: List<TaskSession> = emptyList()
)

class HomeViewModel(
    private val speechProvider: SpeechRecognitionProvider,
    private val ttsProvider: TextToSpeechProvider,
    private val recommendationEngine: RecommendationEngine,
    private val appRegistryRepository: AppRegistryRepository,
    private val taskHistoryRepository: TaskHistoryRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<HomeNavigationEvent>()
    val navigationEvent: SharedFlow<HomeNavigationEvent> = _navigationEvent.asSharedFlow()

    init {
        loadRecents()
        observeSpeechState()
    }

    fun loadRecents() {
        val history = taskHistoryRepository.history.value
        val recentApps = history.mapNotNull { it.selectedAppId }
            .distinct()
            .take(4)
            .mapNotNull { appRegistryRepository.getAppById(it) }

        _uiState.value = _uiState.value.copy(
            recentApps = if (recentApps.isEmpty()) appRegistryRepository.getAllApps().take(4) else recentApps,
            recentTasks = history.take(3)
        )
    }

    private fun observeSpeechState() {
        viewModelScope.launch {
            speechProvider.state.collect { state ->
                when (state) {
                    is SpeechState.Idle -> {
                        if (_uiState.value.orbState == OrbState.LISTENING) {
                            _uiState.value = _uiState.value.copy(
                                orbState = OrbState.IDLE,
                                isListening = false
                            )
                        }
                    }
                    is SpeechState.Listening -> {
                        _uiState.value = _uiState.value.copy(
                            orbState = OrbState.LISTENING,
                            isListening = true,
                            liveTranscript = ""
                        )
                    }
                    is SpeechState.PartialResult -> {
                        _uiState.value = _uiState.value.copy(
                            liveTranscript = state.text
                        )
                    }
                    is SpeechState.FinalResult -> {
                        _uiState.value = _uiState.value.copy(
                            orbState = OrbState.THINKING,
                            isListening = false,
                            liveTranscript = state.text
                        )
                        processQuery(state.text)
                    }
                    is SpeechState.Error -> {
                        _uiState.value = _uiState.value.copy(
                            orbState = OrbState.ERROR_RECOVERY,
                            isListening = false,
                            liveTranscript = state.message
                        )
                    }
                }
            }
        }
    }

    fun onMicClicked() {
        if (_uiState.value.isListening) {
            speechProvider.cancel()
            _uiState.value = _uiState.value.copy(
                orbState = OrbState.IDLE,
                isListening = false,
                liveTranscript = ""
            )
        } else {
            speechProvider.startListening()
        }
    }

    fun onCancelListening() {
        speechProvider.cancel()
        _uiState.value = _uiState.value.copy(
            orbState = OrbState.IDLE,
            isListening = false,
            liveTranscript = ""
        )
    }

    fun submitTextQuery(query: String) {
        if (query.isBlank()) return
        _uiState.value = _uiState.value.copy(
            orbState = OrbState.THINKING,
            liveTranscript = query
        )
        processQuery(query)
    }

    private fun processQuery(query: String) {
        viewModelScope.launch {
            val recommendation = recommendationEngine.analyzeAndRecommend(query)
            val topApp = recommendation.candidates.firstOrNull()

            // If user explicitly asks to open an app (e.g. "Open Perplexity")
            val openAppMatch = recommendation.candidates.firstOrNull { app ->
                query.contains("open ${app.name}", ignoreCase = true) ||
                query.contains("open ${app.id}", ignoreCase = true)
            }

            if (openAppMatch != null) {
                _navigationEvent.emit(HomeNavigationEvent.NavigateToWorkspace(openAppMatch.id, query))
                return@launch
            }

            // Speak greeting explanation if speech response is enabled
            if (settingsRepository.settings.value.spokenResponseEnabled) {
                _uiState.value = _uiState.value.copy(orbState = OrbState.SPEAKING)
                val speechText = if (recommendation.clarificationQuestion != null) {
                    recommendation.clarificationQuestion
                } else if (topApp != null) {
                    "Mujhe ${recommendation.candidates.size} useful tools mile hain. Top recommendation ${topApp.name} hai."
                } else {
                    "Mujhe kuch tools mile hain."
                }

                ttsProvider.speak(speechText) {
                    _uiState.value = _uiState.value.copy(orbState = OrbState.IDLE)
                }
            } else {
                _uiState.value = _uiState.value.copy(orbState = OrbState.IDLE)
            }

            _navigationEvent.emit(HomeNavigationEvent.NavigateToDiscover(query))
        }
    }
}
