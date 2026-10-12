package com.projectdora.v1.ui.workspace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectdora.v1.core.model.ActionStatus
import com.projectdora.v1.core.model.ActionStep
import com.projectdora.v1.core.model.AppEntity
import com.projectdora.v1.core.model.TaskSession
import com.projectdora.v1.core.speech.TextToSpeechProvider
import com.projectdora.v1.data.repository.AppRegistryRepository
import com.projectdora.v1.data.repository.SettingsRepository
import com.projectdora.v1.data.repository.TaskHistoryRepository
import com.projectdora.v1.domain.ActionEngine
import com.projectdora.v1.domain.ActionResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TaskWorkspaceUiState(
    val session: TaskSession? = null,
    val selectedApp: AppEntity? = null,
    val isAppInstalled: Boolean = false,
    val feedbackMessage: String? = null,
    val isExecuting: Boolean = false
)

class TaskWorkspaceViewModel(
    private val actionEngine: ActionEngine,
    private val appRegistryRepository: AppRegistryRepository,
    private val taskHistoryRepository: TaskHistoryRepository,
    private val settingsRepository: SettingsRepository,
    private val ttsProvider: TextToSpeechProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskWorkspaceUiState())
    val uiState: StateFlow<TaskWorkspaceUiState> = _uiState.asStateFlow()

    fun initTask(appId: String, query: String) {
        val app = appRegistryRepository.getAppById(appId) ?: return
        val isInstalled = actionEngine.isPackageInstalled(app.packageName)
        val session = actionEngine.createTaskSession(query, app)

        _uiState.value = TaskWorkspaceUiState(
            session = session,
            selectedApp = app,
            isAppInstalled = isInstalled,
            feedbackMessage = "Task initialized for ${app.name}."
        )

        taskHistoryRepository.saveSession(session)

        // Speak guidance if enabled
        if (settingsRepository.settings.value.spokenResponseEnabled) {
            val speechText = "${app.name} select kiya gaya hai. Main aapko research query prepare karke help karungi."
            ttsProvider.speak(speechText)
        }
    }

    fun executeLaunchDestination() {
        val app = _uiState.value.selectedApp ?: return
        val currentSession = _uiState.value.session ?: return

        _uiState.value = _uiState.value.copy(isExecuting = true)

        val result = actionEngine.launchAppOrUrl(app)
        when (result) {
            is ActionResult.Success -> {
                val updatedSteps = currentSession.steps.toMutableList()
                if (updatedSteps.isNotEmpty()) {
                    updatedSteps[0] = result.step
                }
                val updatedSession = currentSession.copy(
                    steps = updatedSteps,
                    currentStepIndex = 1,
                    status = ActionStatus.VERIFIED
                )
                _uiState.value = _uiState.value.copy(
                    session = updatedSession,
                    feedbackMessage = result.message,
                    isExecuting = false
                )
                taskHistoryRepository.saveSession(updatedSession)
            }
            is ActionResult.Fallback -> {
                val updatedSteps = currentSession.steps.toMutableList()
                if (updatedSteps.isNotEmpty()) {
                    updatedSteps[0] = result.step
                }
                val updatedSession = currentSession.copy(
                    steps = updatedSteps,
                    status = ActionStatus.ATTEMPTED
                )
                _uiState.value = _uiState.value.copy(
                    session = updatedSession,
                    feedbackMessage = result.guidance,
                    isExecuting = false
                )
                taskHistoryRepository.saveSession(updatedSession)
            }
            is ActionResult.Failure -> {
                val updatedSteps = currentSession.steps.toMutableList()
                if (updatedSteps.isNotEmpty()) {
                    updatedSteps[0] = result.step
                }
                val updatedSession = currentSession.copy(
                    steps = updatedSteps,
                    status = ActionStatus.FAILED
                )
                _uiState.value = _uiState.value.copy(
                    session = updatedSession,
                    feedbackMessage = result.error,
                    isExecuting = false
                )
                taskHistoryRepository.saveSession(updatedSession)
            }
        }
    }

    fun copyStagedQuery() {
        val currentSession = _uiState.value.session ?: return
        val stagedPrompt = currentSession.stagedQuery.orEmpty()
        if (stagedPrompt.isBlank()) return

        val result = actionEngine.stageAndCopyQuery(stagedPrompt)
        when (result) {
            is ActionResult.Success -> {
                val updatedSteps = currentSession.steps.toMutableList()
                if (updatedSteps.size > 1) {
                    updatedSteps[1] = result.step
                }
                val updatedSession = currentSession.copy(
                    steps = updatedSteps,
                    currentStepIndex = 2,
                    status = ActionStatus.VERIFIED
                )
                _uiState.value = _uiState.value.copy(
                    session = updatedSession,
                    feedbackMessage = result.message
                )
                taskHistoryRepository.saveSession(updatedSession)

                if (settingsRepository.settings.value.spokenResponseEnabled) {
                    ttsProvider.speak("Query clipboard par copy kar li gayi hai. Ab aap ise search bar mein paste kar sakte hain.")
                }
            }
            else -> {
                _uiState.value = _uiState.value.copy(
                    feedbackMessage = "Failed to copy query to clipboard."
                )
            }
        }
    }

    fun markTaskCompleted() {
        val currentSession = _uiState.value.session ?: return
        val updatedSteps = currentSession.steps.map { it.copy(status = ActionStatus.VERIFIED) }
        val updatedSession = currentSession.copy(
            steps = updatedSteps,
            currentStepIndex = updatedSteps.size,
            status = ActionStatus.COMPLETED
        )
        _uiState.value = _uiState.value.copy(
            session = updatedSession,
            feedbackMessage = "Task verified and marked complete!"
        )
        taskHistoryRepository.saveSession(updatedSession)

        if (settingsRepository.settings.value.spokenResponseEnabled) {
            ttsProvider.speak("Research workflow successfully complete ho gaya hai.")
        }
    }

    fun cancelTask() {
        val currentSession = _uiState.value.session ?: return
        val updatedSession = currentSession.copy(status = ActionStatus.FAILED)
        _uiState.value = _uiState.value.copy(
            session = updatedSession,
            feedbackMessage = "Task cancelled by user."
        )
        taskHistoryRepository.saveSession(updatedSession)
    }
}
