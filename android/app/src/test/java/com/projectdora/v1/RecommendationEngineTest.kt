package com.projectdora.v1

import com.projectdora.v1.data.repository.AppRegistryRepository
import com.projectdora.v1.domain.RecommendationEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RecommendationEngineTest {

    private lateinit var repository: AppRegistryRepository
    private lateinit var engine: RecommendationEngine

    @Before
    fun setUp() {
        repository = AppRegistryRepository()
        engine = RecommendationEngine(repository)
    }

    @Test
    fun testHealthcareAiResearchQuery() {
        val result = engine.analyzeAndRecommend("Find me an AI tool for researching AI in healthcare.", maxResults = 4)
        assertNotNull(result)
        assertTrue(result.isHealthcare)
        assertTrue(result.candidates.isNotEmpty())

        val candidateIds = result.candidates.map { it.id }
        // Should feature perplexity, consensus, or elicit as top matches
        assertTrue(
            "Expected perplexity, consensus, or elicit in top candidates, got: $candidateIds",
            candidateIds.contains("perplexity") || candidateIds.contains("consensus") || candidateIds.contains("elicit")
        )
        assertTrue(result.topRecommendationReason.contains("healthcare", ignoreCase = true))
    }

    @Test
    fun testHinglishResearchQuery() {
        val result = engine.analyzeAndRecommend("Dora, mujhe AI ki help se research karni hai.")
        assertNotNull(result)
        assertTrue(result.candidates.isNotEmpty())
        assertEquals("AI research", result.targetCategory)
    }

    @Test
    fun testStudyExamQuery() {
        val result = engine.analyzeAndRecommend("Help me study for an exam.")
        assertNotNull(result)
        val candidateIds = result.candidates.map { it.id }
        assertTrue(candidateIds.contains("quizlet") || candidateIds.contains("khan_academy"))
    }

    @Test
    fun testVideoEditingQuery() {
        val result = engine.analyzeAndRecommend("Find free video editing apps.")
        assertNotNull(result)
        val candidateIds = result.candidates.map { it.id }
        assertTrue(candidateIds.contains("capcut") || candidateIds.contains("canva"))
    }

    @Test
    fun testApplicationComparison() {
        val comparison = engine.compareApplications(listOf("perplexity", "consensus"))
        assertNotNull(comparison)
        assertEquals(2, comparison.comparedApps.size)
        assertTrue(comparison.criteriaRows.isNotEmpty())
        assertTrue(comparison.topSummary.contains("Perplexity AI"))
        assertTrue(comparison.topSummary.contains("Consensus"))
    }
}
