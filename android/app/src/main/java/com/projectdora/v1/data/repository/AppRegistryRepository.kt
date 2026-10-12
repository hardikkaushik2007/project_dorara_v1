package com.projectdora.v1.data.repository

import com.projectdora.v1.core.model.AppEntity
import com.projectdora.v1.core.model.PricingModel
import com.projectdora.v1.data.local.CuratedCatalogueData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.util.concurrent.TimeUnit

class AppRegistryRepository {

    private val _apps = MutableStateFlow<List<AppEntity>>(CuratedCatalogueData.APPS)
    val apps: StateFlow<List<AppEntity>> = _apps.asStateFlow()

    private val client = OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.SECONDS)
        .readTimeout(2, TimeUnit.SECONDS)
        .build()

    fun getAllApps(): List<AppEntity> = _apps.value

    fun getAppById(id: String): AppEntity? {
        return _apps.value.firstOrNull { it.id.equals(id, ignoreCase = true) }
    }

    fun getCategories(): List<String> {
        return _apps.value.map { it.category }.distinct().sorted()
    }

    fun filterApps(
        category: String? = null,
        searchQuery: String? = null,
        onlyFree: Boolean = false,
        onlyAndroid: Boolean = false
    ): List<AppEntity> {
        var result = _apps.value

        if (!category.isNullOrBlank()) {
            result = result.filter { it.category.equals(category, ignoreCase = true) }
        }

        if (onlyFree) {
            result = result.filter {
                it.pricingModel == PricingModel.FREE || it.pricingModel == PricingModel.FREEMIUM
            }
        }

        if (onlyAndroid) {
            result = result.filter { it.androidAvailable }
        }

        if (!searchQuery.isNullOrBlank()) {
            val q = searchQuery.lowercase().trim()
            result = result.filter { app ->
                app.name.lowercase().contains(q) ||
                app.description.lowercase().contains(q) ||
                app.bestUseCase.lowercase().contains(q) ||
                app.tags.any { it.lowercase().contains(q) }
            }
        }

        return result
    }

    suspend fun syncWithBackend(backendUrl: String): Boolean {
        return try {
            val request = Request.Builder()
                .url("$backendUrl/api/v1/apps")
                .get()
                .build()
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                // If backend has fresh entries, we can use them
                response.close()
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }
}
