package com.projectdora.v1.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.projectdora.v1.ui.activity.ActivityHistoryScreen
import com.projectdora.v1.ui.activity.ActivityViewModel
import com.projectdora.v1.ui.compare.CompareScreen
import com.projectdora.v1.ui.compare.CompareViewModel
import com.projectdora.v1.ui.components.DoraBottomNav
import com.projectdora.v1.ui.components.DoraTopBar
import com.projectdora.v1.ui.discover.DiscoverScreen
import com.projectdora.v1.ui.discover.DiscoverViewModel
import com.projectdora.v1.ui.home.HomeScreen
import com.projectdora.v1.ui.home.HomeViewModel
import com.projectdora.v1.ui.settings.SettingsScreen
import com.projectdora.v1.ui.settings.SettingsViewModel
import com.projectdora.v1.ui.theme.NavyBackground
import com.projectdora.v1.ui.workspace.TaskWorkspaceScreen
import com.projectdora.v1.ui.workspace.TaskWorkspaceViewModel

@Composable
fun DoraNavHost(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    discoverViewModel: DiscoverViewModel,
    compareViewModel: CompareViewModel,
    workspaceViewModel: TaskWorkspaceViewModel,
    activityViewModel: ActivityViewModel,
    settingsViewModel: SettingsViewModel,
    isSpeaking: Boolean,
    isDemoMode: Boolean,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route

    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.Discover.route,
        Screen.Activity.route,
        Screen.Settings.route
    )

    Scaffold(
        topBar = {
            DoraTopBar(
                isSpeaking = isSpeaking,
                isDemoMode = isDemoMode
            )
        },
        bottomBar = {
            if (showBottomBar) {
                val cleanRoute = when {
                    currentRoute.startsWith("discover") -> "discover"
                    currentRoute.startsWith("activity") -> "activity"
                    currentRoute.startsWith("settings") -> "settings"
                    else -> "home"
                }
                DoraBottomNav(
                    currentRoute = cleanRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(Screen.Home.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        },
        containerColor = NavyBackground,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Home Destination
            composable(Screen.Home.route) {
                HomeScreen(
                    viewModel = homeViewModel,
                    onNavigateToDiscover = { query ->
                        navController.navigate(Screen.Discover.createRoute(query))
                    },
                    onNavigateToWorkspace = { appId, query ->
                        navController.navigate(Screen.TaskWorkspace.createRoute(appId, query))
                    }
                )
            }

            // Discover Destination
            composable(
                route = Screen.Discover.route,
                arguments = listOf(
                    navArgument("query") {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                )
            ) { backStackEntry ->
                val query = backStackEntry.arguments?.getString("query").orEmpty()
                DiscoverScreen(
                    query = query,
                    viewModel = discoverViewModel,
                    onOpenAppWorkspace = { appId, q ->
                        navController.navigate(Screen.TaskWorkspace.createRoute(appId, q))
                    },
                    onCompareApps = { appIds ->
                        navController.navigate(Screen.Compare.createRoute(appIds))
                    }
                )
            }

            // Compare Destination
            composable(
                route = Screen.Compare.route,
                arguments = listOf(
                    navArgument("appIds") {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                )
            ) { backStackEntry ->
                val appIdsString = backStackEntry.arguments?.getString("appIds").orEmpty()
                val appIds = appIdsString.split(",").filter { it.isNotBlank() }
                CompareScreen(
                    appIds = appIds,
                    viewModel = compareViewModel,
                    onBackClick = { navController.popBackStack() },
                    onSelectApp = { appId ->
                        navController.navigate(Screen.TaskWorkspace.createRoute(appId, "Research with selected tool"))
                    }
                )
            }

            // Task Workspace Destination
            composable(
                route = Screen.TaskWorkspace.route,
                arguments = listOf(
                    navArgument("appId") { type = NavType.StringType },
                    navArgument("query") {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                )
            ) { backStackEntry ->
                val appId = backStackEntry.arguments?.getString("appId").orEmpty()
                val query = backStackEntry.arguments?.getString("query").orEmpty()
                TaskWorkspaceScreen(
                    appId = appId,
                    query = query,
                    viewModel = workspaceViewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }

            // Activity Destination
            composable(Screen.Activity.route) {
                ActivityHistoryScreen(
                    viewModel = activityViewModel,
                    onReopenTask = { appId, q ->
                        navController.navigate(Screen.TaskWorkspace.createRoute(appId, q))
                    }
                )
            }

            // Settings Destination
            composable(Screen.Settings.route) {
                SettingsScreen(viewModel = settingsViewModel)
            }
        }
    }
}
