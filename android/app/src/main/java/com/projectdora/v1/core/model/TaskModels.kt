package com.projectdora.v1.core.model

data class ActionStep(
    val stepId: String,
    val title: String,
    val description: String,
    val status: ActionStatus,
    val executionMethod: String,
    val actionType: String,
    val observableSuccessCriteria: String,
    val actualResult: String? = null,
    val recoveryGuidance: String? = null
)

data class TaskSession(
    val sessionId: String,
    val originalRequest: String,
    val selectedAppId: String? = null,
    val selectedAppName: String? = null,
    val status: ActionStatus = ActionStatus.REQUESTED,
    val currentStepIndex: Int = 0,
    val stagedQuery: String? = null,
    val steps: List<ActionStep> = emptyList(),
    val createdAt: Long = System.currentTimeMillis()
)
