package com.projectdora.v1.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Discover : Screen("discover?query={query}") {
        fun createRoute(query: String = "") = "discover?query=$query"
    }
    object Compare : Screen("compare?appIds={appIds}") {
        fun createRoute(appIds: List<String>) = "compare?appIds=${appIds.joinToString(",")}"
    }
    object TaskWorkspace : Screen("workspace/{appId}?query={query}") {
        fun createRoute(appId: String, query: String) = "workspace/$appId?query=$query"
    }
    object Activity : Screen("activity")
    object Settings : Screen("settings")
}
