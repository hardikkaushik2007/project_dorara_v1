package com.projectdora.v1.ui.activity

import androidx.lifecycle.ViewModel
import com.projectdora.v1.core.model.TaskSession
import com.projectdora.v1.data.repository.TaskHistoryRepository
import kotlinx.coroutines.flow.StateFlow

class ActivityViewModel(
    private val taskHistoryRepository: TaskHistoryRepository
) : ViewModel() {

    val history: StateFlow<List<TaskSession>> = taskHistoryRepository.history

    fun clearAllHistory() {
        taskHistoryRepository.clearHistory()
    }
}
