package com.projectdora.v1

import com.projectdora.v1.data.local.CuratedCatalogueData
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AppCatalogueValidationTest {

    @Test
    fun testAllCatalogueEntriesAreVerified() {
        val apps = CuratedCatalogueData.APPS
        assertTrue("Catalogue must contain at least 15 verified apps", apps.size >= 15)

        apps.forEach { app ->
            // Official URLs must be secure HTTPS
            assertTrue(
                "App ${app.name} official URL must start with https://, was: ${app.officialUrl}",
                app.officialUrl.startsWith("https://")
            )

            // Pricing URLs must be secure HTTPS
            assertTrue(
                "App ${app.name} pricing URL must start with https://, was: ${app.pricingSourceUrl}",
                app.pricingSourceUrl.startsWith("https://")
            )

            // Known limitations must be present and honest
            assertFalse(
                "App ${app.name} must specify known limitations honestly",
                app.knownLimitations.isEmpty()
            )

            // Last verified date format
            assertTrue(
                "App ${app.name} last verified date must match YYYY-MM-DD",
                app.lastVerifiedDate.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))
            )

            // Automation capabilities must be defined
            assertTrue(
                "App ${app.name} must declare its automation capabilities",
                app.automationCapabilities.isNotEmpty()
            )
        }
    }
}
