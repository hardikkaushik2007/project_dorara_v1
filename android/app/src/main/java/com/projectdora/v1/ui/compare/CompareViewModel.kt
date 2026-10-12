package com.projectdora.v1.ui.compare

import androidx.lifecycle.ViewModel
import com.projectdora.v1.core.model.AppEntity
import com.projectdora.v1.domain.ComparisonResult
import com.projectdora.v1.domain.RecommendationEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CompareUiState(
    val comparisonResult: ComparisonResult? = null,
    val selectedApps: List<AppEntity> = emptyList()
)

class CompareViewModel(
    private val recommendationEngine: RecommendationEngine
) : ViewModel() {

    private val _uiState = MutableStateFlow(CompareUiState())
    val uiState: StateFlow<CompareUiState> = _uiState.asStateFlow()

    fun loadComparison(appIds: List<String>) {
        val result = recommendationEngine.compareApplications(appIds)
        _uiState.value = CompareUiState(
            comparisonResult = result,
            selectedApps = result.comparedApps
        )
    }
}
