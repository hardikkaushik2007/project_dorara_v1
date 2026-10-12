package com.projectdora.v1.core.model

data class AppEntity(
    val id: String,
    val name: String,
    val category: String,
    val officialUrl: String,
    val packageName: String? = null,
    val description: String,
    val bestUseCase: String,
    val suitabilityExplanation: String,
    val pricingModel: PricingModel,
    val pricingDetails: String,
    val pricingSourceUrl: String,
    val lastVerifiedDate: String,
    val androidAvailable: Boolean,
    val knownLimitations: List<String>,
    val automationCapabilities: List<AutomationCapability>,
    val tags: List<String>,
    val confidenceScore: Float = 1.0f
)
