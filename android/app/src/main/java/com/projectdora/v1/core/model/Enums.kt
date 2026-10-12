package com.projectdora.v1.core.model

enum class PricingModel(val displayName: String) {
    FREE("Free"),
    FREEMIUM("Freemium"),
    PAID("Paid"),
    FREE_TRIAL("Free Trial"),
    UNKNOWN("Unknown")
}

enum class AutomationCapability(val label: String) {
    DEEP_LINK_LAUNCH("Direct App Deep Link"),
    WEB_BROWSER_LAUNCH("Verified HTTPS Browser Launch"),
    CLIPBOARD_QUERY_STAGING("Assisted Query Staging & Copy"),
    ASSISTED_FORM_FILLING("Assisted Form Filling"),
    MANUAL_GUIDANCE("Guided Step-by-Step Manual Fallback")
}

enum class ActionStatus(val label: String) {
    REQUESTED("Requested"),
    ATTEMPTED("Attempted"),
    COMPLETED("Completed"),
    VERIFIED("Verified"),
    FAILED("Failed")
}

enum class OrbState {
    IDLE,
    LISTENING,
    THINKING,
    PRESENTING,
    EXECUTING,
    SPEAKING,
    ERROR_RECOVERY
}
