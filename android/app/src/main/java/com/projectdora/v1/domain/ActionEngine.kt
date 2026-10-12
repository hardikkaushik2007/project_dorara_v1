package com.projectdora.v1.domain

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.projectdora.v1.core.model.ActionStatus
import com.projectdora.v1.core.model.ActionStep
import com.projectdora.v1.core.model.AppEntity
import com.projectdora.v1.core.model.TaskSession
import java.util.UUID

sealed class ActionResult {
    data class Success(val step: ActionStep, val message: String) : ActionResult()
    data class Fallback(val step: ActionStep, val guidance: String) : ActionResult()
    data class Failure(val step: ActionStep, val error: String) : ActionResult()
}

class ActionEngine(
    private val context: Context
) {

    fun isPackageInstalled(packageName: String?): Boolean {
        if (packageName.isNullOrBlank()) return false
        return try {
            context.packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun createTaskSession(userQuery: String, app: AppEntity): TaskSession {
        val steps = mutableListOf<ActionStep>()

        // Step 1: Destination Launching
        val isInstalled = isPackageInstalled(app.packageName)
        val launchMethod = if (isInstalled) "Native Android App Intent" else "Verified HTTPS Custom Tab"
        steps.add(
            ActionStep(
                stepId = UUID.randomUUID().toString(),
                title = "Launch ${app.name}",
                description = if (isInstalled) {
                    "Open installed ${app.name} native Android application"
                } else {
                    "Open official verified destination (${app.officialUrl})"
                },
                status = ActionStatus.REQUESTED,
                executionMethod = launchMethod,
                actionType = "APP_LAUNCH",
                observableSuccessCriteria = "Destination window opened without crash or intent error",
                actualResult = null,
                recoveryGuidance = "If browser fails to load, verify network connection or open directly in Chrome."
            )
        )

        // Step 2: Query Preparation
        steps.add(
            ActionStep(
                stepId = UUID.randomUUID().toString(),
                title = "Stage Research Query",
                description = "Format user request into an optimized prompt and copy to clipboard",
                status = ActionStatus.REQUESTED,
                executionMethod = "Android Clipboard Framework",
                actionType = "CLIPBOARD_STAGE",
                observableSuccessCriteria = "Formatted prompt copied to Android system clipboard",
                actualResult = null,
                recoveryGuidance = "You can manually type or paste the prompt into the search bar."
            )
        )

        // Step 3: Guided Execution
        steps.add(
            ActionStep(
                stepId = UUID.randomUUID().toString(),
                title = "Guided Research & Review",
                description = "Dora guides you to paste prompt, review cited sources, and inspect findings.",
                status = ActionStatus.REQUESTED,
                executionMethod = "User-Assisted Guided Flow",
                actionType = "GUIDED_MANUAL",
                observableSuccessCriteria = "User reviews research citations and marks task complete",
                actualResult = null,
                recoveryGuidance = "Paste the copied prompt into ${app.name}'s query box, tap Search, and inspect citations."
            )
        )

        val stagedQuery = generateStagedPrompt(userQuery, app)

        return TaskSession(
            sessionId = UUID.randomUUID().toString(),
            originalRequest = userQuery,
            selectedAppId = app.id,
            selectedAppName = app.name,
            status = ActionStatus.REQUESTED,
            currentStepIndex = 0,
            stagedQuery = stagedQuery,
            steps = steps
        )
    }

    private fun generateStagedPrompt(userQuery: String, app: AppEntity): String {
        return when {
            userQuery.contains("healthcare", ignoreCase = true) -> {
                "Recent clinical trials and verified applications of artificial intelligence in healthcare diagnostics and treatments."
            }
            userQuery.contains("study", ignoreCase = true) || userQuery.contains("exam", ignoreCase = true) -> {
                "High-yield study outline, key concept flashcards, and summary practice questions for upcoming exam preparation."
            }
            userQuery.contains("code", ignoreCase = true) || userQuery.contains("website", ignoreCase = true) -> {
                "Clean, production-ready implementation plan and modern starter code architecture."
            }
            else -> {
                "Comprehensive research and key summary: $userQuery"
            }
        }
    }

    fun launchAppOrUrl(app: AppEntity): ActionResult {
        val isInstalled = isPackageInstalled(app.packageName)
        val step = ActionStep(
            stepId = UUID.randomUUID().toString(),
            title = "Launch ${app.name}",
            description = "Opening ${app.name}",
            status = ActionStatus.ATTEMPTED,
            executionMethod = if (isInstalled) "Native Package Launch" else "CustomTabs HTTPS Launch",
            actionType = "APP_LAUNCH",
            observableSuccessCriteria = "Target activity started"
        )

        try {
            if (isInstalled && app.packageName != null) {
                val launchIntent = context.packageManager.getLaunchIntentForPackage(app.packageName)
                if (launchIntent != null) {
                    launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(launchIntent)
                    return ActionResult.Success(
                        step = step.copy(
                            status = ActionStatus.VERIFIED,
                            actualResult = "Launched native app: ${app.packageName}"
                        ),
                        message = "Opened installed ${app.name} app"
                    )
                }
            }

            // Fallback to verified official HTTPS URL
            val uri = Uri.parse(app.officialUrl)
            val customTabsIntent = CustomTabsIntent.Builder().build()
            customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            customTabsIntent.launchUrl(context, uri)

            return ActionResult.Success(
                step = step.copy(
                    status = ActionStatus.VERIFIED,
                    actualResult = "Launched verified HTTPS Custom Tab: ${app.officialUrl}"
                ),
                message = "Opened verified website: ${app.officialUrl}"
            )
        } catch (e: Exception) {
            // General VIEW fallback
            return try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(app.officialUrl)).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
                ActionResult.Success(
                    step = step.copy(
                        status = ActionStatus.COMPLETED,
                        actualResult = "Launched via browser fallback intent"
                    ),
                    message = "Opened in browser"
                )
            } catch (fallbackEx: Exception) {
                ActionResult.Failure(
                    step = step.copy(
                        status = ActionStatus.FAILED,
                        actualResult = "Failed to open destination: ${fallbackEx.message}",
                        recoveryGuidance = "Copy URL (${app.officialUrl}) manually into your preferred browser."
                    ),
                    error = "Could not open destination: ${fallbackEx.message}"
                )
            }
        }
    }

    fun stageAndCopyQuery(query: String): ActionResult {
        val step = ActionStep(
            stepId = UUID.randomUUID().toString(),
            title = "Copy Prepared Query",
            description = "Copy query to system clipboard",
            status = ActionStatus.ATTEMPTED,
            executionMethod = "Android ClipboardManager",
            actionType = "CLIPBOARD_STAGE",
            observableSuccessCriteria = "Primary clip updated"
        )

        return try {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Dora Research Query", query)
            clipboard.setPrimaryClip(clip)

            ActionResult.Success(
                step = step.copy(
                    status = ActionStatus.VERIFIED,
                    actualResult = "Query copied to clipboard: '$query'"
                ),
                message = "Query copied to clipboard! You can paste it into the search box."
            )
        } catch (e: Exception) {
            ActionResult.Failure(
                step = step.copy(
                    status = ActionStatus.FAILED,
                    actualResult = "Failed to copy query: ${e.message}"
                ),
                error = "Clipboard access failed"
            )
        }
    }
}
