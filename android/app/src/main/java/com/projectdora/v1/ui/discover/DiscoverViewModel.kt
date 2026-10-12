package com.projectdora.v1.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectdora.v1.core.model.AppEntity
import com.projectdora.v1.core.model.PricingModel
import com.projectdora.v1.data.repository.AppRegistryRepository
import com.projectdora.v1.domain.RecommendationEngine
import com.projectdora.v1.domain.RecommendationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class DiscoverFilter(val label: String) {
    BEST_MATCH("Best Match"),
    FREE_TIER("Free Tier"),
    ANDROID_APPS("Android Apps"),
    WEB_APPS("Web Apps")
}

data class DiscoverUiState(
    val query: String = "",
    val rationale: String = "",
    val allRecommendations: List<AppEntity> = emptyList(),
    val filteredItems: List<AppEntity> = emptyList(),
    val activeFilter: DiscoverFilter = DiscoverFilter.BEST_MATCH,
    val selectedForCompare: Set<String> = emptySet(),
    val isLoading: Boolean = false
)

class DiscoverViewModel(
    private val recommendationEngine: RecommendationEngine,
    private val appRegistryRepository: AppRegistryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DiscoverUiState())
    val uiState: StateFlow<DiscoverUiState> = _uiState.asStateFlow()

    fun loadRecommendations(query: String) {
        val effectiveQuery = if (query.isBlank()) "AI research tools" else query
        _uiState.value = _uiState.value.copy(
            query = effectiveQuery,
            isLoading = true
        )

        viewModelScope.launch {
            val result: RecommendationResult = recommendationEngine.analyzeAndRecommend(effectiveQuery, maxResults = 8)
            _uiState.value = _uiState.value.copy(
                rationale = result.topRecommendationReason,
                allRecommendations = result.candidates,
                filteredItems = applyFilter(result.candidates, _uiState.value.activeFilter),
                isLoading = false
            )
        }
    }

    fun setFilter(filter: DiscoverFilter) {
        _uiState.value = _uiState.value.copy(
            activeFilter = filter,
            filteredItems = applyFilter(_uiState.value.allRecommendations, filter)
        )
    }

    private fun applyFilter(items: List<AppEntity>, filter: DiscoverFilter): List<AppEntity> {
        return when (filter) {
            DiscoverFilter.BEST_MATCH -> items
            DiscoverFilter.FREE_TIER -> items.filter {
                it.pricingModel == PricingModel.FREE || it.pricingModel == PricingModel.FREEMIUM
            }
            DiscoverFilter.ANDROID_APPS -> items.filter { it.androidAvailable }
            DiscoverFilter.WEB_APPS -> items.filter { !it.androidAvailable }
        }
    }

    fun toggleCompareSelection(appId: String) {
        val current = _uiState.value.selectedForCompare.toMutableSet()
        if (current.contains(appId)) {
            current.remove(appId)
        } else {
            if (current.size < 3) {
                current.add(appId)
            }
        }
        _uiState.value = _uiState.value.copy(selectedForCompare = current)
    }
}
