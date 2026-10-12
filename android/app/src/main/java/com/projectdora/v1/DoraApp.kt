package com.projectdora.v1

import android.app.Application
import com.projectdora.v1.core.speech.AndroidSpeechRecognizerProvider
import com.projectdora.v1.core.speech.AndroidTtsProvider
import com.projectdora.v1.core.speech.DemoSpeechRecognizerProvider
import com.projectdora.v1.core.speech.SpeechRecognitionProvider
import com.projectdora.v1.core.speech.TextToSpeechProvider
import com.projectdora.v1.data.repository.AppRegistryRepository
import com.projectdora.v1.data.repository.SettingsRepository
import com.projectdora.v1.data.repository.TaskHistoryRepository
import com.projectdora.v1.domain.ActionEngine
import com.projectdora.v1.domain.RecommendationEngine

class DoraApp : Application() {

    lateinit var appRegistryRepository: AppRegistryRepository
        private set
    lateinit var taskHistoryRepository: TaskHistoryRepository
        private set
    lateinit var settingsRepository: SettingsRepository
        private set
    lateinit var recommendationEngine: RecommendationEngine
        private set
    lateinit var actionEngine: ActionEngine
        private set
    lateinit var nativeSpeechProvider: AndroidSpeechRecognizerProvider
        private set
    lateinit var demoSpeechProvider: DemoSpeechRecognizerProvider
        private set
    lateinit var ttsProvider: TextToSpeechProvider
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this

        appRegistryRepository = AppRegistryRepository()
        taskHistoryRepository = TaskHistoryRepository(this)
        settingsRepository = SettingsRepository(this)
        recommendationEngine = RecommendationEngine(appRegistryRepository)
        actionEngine = ActionEngine(this)

        nativeSpeechProvider = AndroidSpeechRecognizerProvider(this)
        demoSpeechProvider = DemoSpeechRecognizerProvider()
        ttsProvider = AndroidTtsProvider(this)
    }

    fun getActiveSpeechProvider(): SpeechRecognitionProvider {
        return if (settingsRepository.settings.value.demoModeEnabled || !nativeSpeechProvider.isAvailable()) {
            demoSpeechProvider
        } else {
            nativeSpeechProvider
        }
    }

    companion object {
        lateinit var instance: DoraApp
            private set
    }
}
