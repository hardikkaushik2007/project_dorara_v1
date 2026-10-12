package com.projectdora.v1.domain

import com.projectdora.v1.core.model.AppEntity
import com.projectdora.v1.core.model.PricingModel
import com.projectdora.v1.data.repository.AppRegistryRepository

data class RecommendationResult(
    val query: String,
    val targetCategory: String,
    val topRecommendationReason: String,
    val candidates: List<AppEntity>,
    val isHealthcare: Boolean = false,
    val isAcademic: Boolean = false,
    val isCoding: Boolean = false,
    val clarificationQuestion: String? = null
)

data class ComparisonResult(
    val comparedApps: List<AppEntity>,
    val topSummary: String,
    val criteriaRows: List<ComparisonRow>
)

data class ComparisonRow(
    val title: String,
    val appValues: Map<String, String>
)

class RecommendationEngine(
    private val appRegistryRepository: AppRegistryRepository
) {

    private val categoryKeywords = mapOf(
        "AI research" to listOf("ai", "research", "search", "answer", "perplexity", "chatgpt", "claude", "synthesis"),
        "Academic research" to listOf("academic", "paper", "papers", "study", "clinical", "healthcare", "medicine", "consensus", "elicit", "scholar", "literature", "citations"),
        "Studying and education" to listOf("study", "exam", "education", "quizlet", "khan", "learn", "flashcards", "math"),
        "Coding" to listOf("code", "coding", "developer", "programming", "python", "javascript", "website", "build a website", "github", "replit", "bug"),
        "Writing" to listOf("write", "writing", "essay", "grammar", "paraphrase", "email", "proofread", "grammarly", "quillbot"),
        "Image and video creation" to listOf("image", "video", "photo", "edit video", "editing", "canva", "capcut", "graphic", "reels", "shorts"),
        "Documents and PDFs" to listOf("pdf", "document", "contract", "chatpdf", "acrobat", "read pdf", "summarize pdf", "explain a pdf"),
        "Productivity" to listOf("productivity", "notes", "todo", "task", "organize", "habits", "notion", "todoist")
    )

    fun analyzeAndRecommend(query: String, maxResults: Int = 4): RecommendationResult {
        val qLower = query.lowercase().trim()
        val allApps = appRegistryRepository.getAllApps()

        val isHealthcare = listOf("healthcare", "health", "medicine", "clinical", "doctor", "medical", "disease")
            .any { qLower.contains(it) }
        val isAcademic = listOf("academic", "paper", "papers", "literature", "journal", "citation", "peer-reviewed")
            .any { qLower.contains(it) }
        val isCoding = listOf("code", "coding", "program", "developer", "website", "bug", "software")
            .any { qLower.contains(it) }

        // Category scoring
        val scores = mutableMapOf<String, Int>()
        categoryKeywords.keys.forEach { scores[it] = 0 }

        for ((cat, keywords) in categoryKeywords) {
            for (kw in keywords) {
                if (qLower.contains(kw)) {
                    scores[cat] = (scores[cat] ?: 0) + 2
                }
            }
        }

        if (isHealthcare || isAcademic) {
            scores["Academic research"] = (scores["Academic research"] ?: 0) + 5
            scores["AI research"] = (scores["AI research"] ?: 0) + 3
        }

        if (qLower.contains("pdf")) {
            scores["Documents and PDFs"] = (scores["Documents and PDFs"] ?: 0) + 6
        }

        val targetCategory = scores.maxByOrNull { it.value }?.key ?: "AI research"

        // Score candidates
        val scoredList = allApps.map { app ->
            var score = 0.0f
            if (qLower.contains(app.name.lowercase())) score += 30f
            if (app.category.equals(targetCategory, ignoreCase = true)) score += 15f

            if (isHealthcare) {
                if (app.tags.any { it.contains("healthcare") || it.contains("clinical") || it.contains("medicine") }) {
                    score += 12f
                }
                if (app.id in listOf("perplexity", "consensus", "elicit")) score += 8f
            }

            if (isAcademic && app.category == "Academic research") score += 10f

            app.tags.forEach { tag ->
                if (qLower.contains(tag.lowercase())) score += 5f
            }

            Pair(app, score)
        }.sortedByDescending { it.second }

        val topCandidates = scoredList.take(maxResults).map { it.first }

        // Rationale synthesis
        val topApp = topCandidates.firstOrNull()
        val rationale = when {
            topApp == null -> "No verified applications matched the query criteria."
            isHealthcare && topApp.id in listOf("perplexity", "consensus", "elicit") -> {
                "For healthcare and clinical research, ${topApp.name} is recommended because it " +
                "provides direct scientific source citations. If you need peer-reviewed evidence synthesis, " +
                "Consensus or Elicit offer specialized clinical extraction."
            }
            targetCategory == "Coding" -> {
                "${topApp.name} suits this workflow best based on its developer-specific synthesis " +
                "and rapid execution capabilities."
            }
            else -> {
                "${topApp.name} is ranked top for '$query' based on verified feature fit: ${topApp.bestUseCase}"
            }
        }

        var clarification: String? = null
        if (qLower in listOf("research", "ai", "study", "help", "tools", "find tool")) {
            clarification = "Kya tum general web research karna chahte ho ya academic papers par focus karna hai?"
        }

        return RecommendationResult(
            query = query,
            targetCategory = targetCategory,
            topRecommendationReason = rationale,
            candidates = topCandidates,
            isHealthcare = isHealthcare,
            isAcademic = isAcademic,
            isCoding = isCoding,
            clarificationQuestion = clarification
        )
    }

    fun compareApplications(appIds: List<String>): ComparisonResult {
        val apps = appIds.mapNotNull { appRegistryRepository.getAppById(it) }
        if (apps.isEmpty()) {
            return ComparisonResult(
                comparedApps = emptyList(),
                topSummary = "No valid applications selected for comparison.",
                criteriaRows = emptyList()
            )
        }

        val rows = listOf(
            ComparisonRow("Best Use Case", apps.associate { it.name to it.bestUseCase }),
            ComparisonRow("Pricing Model", apps.associate { it.name to "${it.pricingModel.displayName}: ${it.pricingDetails}" }),
            ComparisonRow("Limitations", apps.associate { it.name to it.knownLimitations.joinToString("; ") }),
            ComparisonRow("Android Availability", apps.associate { it.name to if (it.androidAvailable) "Yes (Native App)" else "Web Only (Mobile Browser)" }),
            ComparisonRow("Automation Level", apps.associate { it.name to it.automationCapabilities.joinToString(", ") { cap -> cap.label } }),
            ComparisonRow("Pricing Link", apps.associate { it.name to it.pricingSourceUrl }),
            ComparisonRow("Last Verified", apps.associate { it.name to it.lastVerifiedDate })
        )

        val topSummary = if (apps.size >= 2) {
            "Comparing ${apps.joinToString(" and ") { it.name }}: ${apps[0].name} excels at ${apps[0].bestUseCase}, " +
            "while ${apps[1].name} is specialized for ${apps[1].bestUseCase}. Choose based on whether native Android execution or deep literature extraction is your primary priority."
        } else {
            "${apps[0].name} is verified for ${apps[0].bestUseCase}."
        }

        return ComparisonResult(
            comparedApps = apps,
            topSummary = topSummary,
            criteriaRows = rows
        )
    }
}
