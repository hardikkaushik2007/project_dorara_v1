package com.projectdora.v1

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.projectdora.v1.ui.activity.ActivityViewModel
import com.projectdora.v1.ui.compare.CompareViewModel
import com.projectdora.v1.ui.discover.DiscoverViewModel
import com.projectdora.v1.ui.home.HomeViewModel
import com.projectdora.v1.ui.navigation.DoraNavHost
import com.projectdora.v1.ui.settings.SettingsViewModel
import com.projectdora.v1.ui.theme.NavyBackground
import com.projectdora.v1.ui.theme.ProjectDoraTheme
import com.projectdora.v1.ui.workspace.TaskWorkspaceViewModel

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        // Graceful permission handling: if denied, speech switches gracefully to Demo provider
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check audio recording permission safely
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }

        val app = application as DoraApp

        val homeViewModel = HomeViewModel(
            speechProvider = app.getActiveSpeechProvider(),
            ttsProvider = app.ttsProvider,
            recommendationEngine = app.recommendationEngine,
            appRegistryRepository = app.appRegistryRepository,
            taskHistoryRepository = app.taskHistoryRepository,
            settingsRepository = app.settingsRepository
        )

        val discoverViewModel = DiscoverViewModel(
            recommendationEngine = app.recommendationEngine,
            appRegistryRepository = app.appRegistryRepository
        )

        val compareViewModel = CompareViewModel(
            recommendationEngine = app.recommendationEngine
        )

        val workspaceViewModel = TaskWorkspaceViewModel(
            actionEngine = app.actionEngine,
            appRegistryRepository = app.appRegistryRepository,
            taskHistoryRepository = app.taskHistoryRepository,
            settingsRepository = app.settingsRepository,
            ttsProvider = app.ttsProvider
        )

        val activityViewModel = ActivityViewModel(
            taskHistoryRepository = app.taskHistoryRepository
        )

        val settingsViewModel = SettingsViewModel(
            settingsRepository = app.settingsRepository,
            taskHistoryRepository = app.taskHistoryRepository
        )

        setContent {
            ProjectDoraTheme {
                val isSpeaking by app.ttsProvider.isSpeaking.collectAsState()
                val settings by app.settingsRepository.settings.collectAsState()
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = NavyBackground
                ) {
                    DoraNavHost(
                        navController = navController,
                        homeViewModel = homeViewModel,
                        discoverViewModel = discoverViewModel,
                        compareViewModel = compareViewModel,
                        workspaceViewModel = workspaceViewModel,
                        activityViewModel = activityViewModel,
                        settingsViewModel = settingsViewModel,
                        isSpeaking = isSpeaking,
                        isDemoMode = settings.demoModeEnabled
                    )
                }
            }
        }
    }
}
