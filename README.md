# Project Dora V1 🌌
### A Futuristic Voice-First AI Companion for App Discovery & Guided Automation

Project Dora is a futuristic, voice-first Android assistant built with Jetpack Compose, Material 3, and Kotlin Coroutines, coupled with a modular FastAPI companion backend. Inspired by the idea of an intelligent companion that finds the right digital tool for any genuine user request, Dora understands intent, compares options side by side, verifies pricing and limitations, safely launches applications or verified official URLs, and guides users through execution.

---

## 🌟 Core Features

- **Voice-First Interaction**:
  - Voice input with live transcript streaming and text fallback.
  - Native Android SpeechRecognizer + simulated demo provider for emulators/offline testing.
  - Android TextToSpeech spoken responses in English and Hinglish (*"Hey! Main Dora hoon. Batao, aaj kya karna hai?"*).
- **Futuristic Reactive Voice Orb**:
  - Custom Canvas-rendered animated companion avatar with 7 active states: `IDLE`, `LISTENING`, `THINKING`, `PRESENTING`, `EXECUTING`, `SPEAKING`, and `ERROR_RECOVERY`.
- **Curated Verified App Catalogue (20+ Apps)**:
  - Curated across AI Research, Academic Research, Studying, Coding, Writing, Image & Video Creation, Productivity, and Documents & PDFs.
  - Honest pricing disclosures (Free, Freemium, Paid), official pricing source URLs, and explicit known limitations.
- **Side-by-Side Application Comparison**:
  - Evidence-based comparative analysis across task suitability, free tier limitations, paid capabilities, and platform availability.
- **Capability-Driven Action Engine**:
  - Gated state transitions: `REQUESTED` $\rightarrow$ `ATTEMPTED` $\rightarrow$ `COMPLETED` / `VERIFIED`.
  - Safe HTTPS CustomTabs and installed package intent launching.
  - Query staging with 1-click clipboard copy.
  - Step-by-step guided instructions when third-party apps restrict automated form submission.
- **Privacy & Security**:
  - No background microphone access.
  - No invasive accessibility abuse.
  - Complete task history deletion and offline-first local mode.

---

## 📱 Navigation & Screens

1. **Home Screen**: Reactive Orb, natural greeting, voice & text bar, suggestion chips, recent tools, and recent task summaries.
2. **Discover Screen**: Categorized cards with best use case, suitability rationale, pricing badges, and comparison toggles.
3. **Compare Screen**: Responsive stacked comparison matrix with Dora's top verdict.
4. **Task Workspace**: Original request context, staged research prompt, execution workflow verification, and step-by-step guidance.
5. **Activity Screen**: Persistent history of completed tasks with reopen and clear options.
6. **Settings Screen**: Language toggle (English/Hinglish), spoken response switch, speech rate slider, simulated demo mode, and privacy policies.

---

## 🏗️ Architecture

```
Project_Dorara_V1/
├── android/                             # Native Android Application (Jetpack Compose)
│   ├── app/src/main/java/com/projectdora/v1/
│   │   ├── core/model/                 # AppEntity, PricingModel, TaskModels, OrbState
│   │   ├── core/speech/                # SpeechRecognizer & TextToSpeech providers
│   │   ├── data/local/                 # Curated offline catalogue (20+ apps)
│   │   ├── data/repository/            # AppRegistry, TaskHistory, Settings repositories
│   │   ├── domain/                     # RecommendationEngine, ActionEngine
│   │   ├── ui/components/              # DoraOrb, AppCard, VoiceBar, StatusPill, DoraBottomNav
│   │   ├── ui/home/                    # HomeScreen & HomeViewModel
│   │   ├── ui/discover/                # DiscoverScreen & DiscoverViewModel
│   │   ├── ui/compare/                 # CompareScreen & CompareViewModel
│   │   ├── ui/workspace/               # TaskWorkspaceScreen & TaskWorkspaceViewModel
│   │   ├── ui/activity/                # ActivityHistoryScreen & ActivityViewModel
│   │   ├── ui/settings/                # SettingsScreen & SettingsViewModel
│   │   ├── DoraApp.kt                  # Application class
│   │   └── MainActivity.kt             # Main Activity entry point
│   └── app/src/test/java/              # Automated unit tests
├── backend/                             # Companion FastAPI Backend Service
│   ├── app/
│   │   ├── main.py                     # REST API routes
│   │   ├── models.py                   # Pydantic data schemas
│   │   ├── catalogue.py                # Verified application catalogue
│   │   └── engine.py                   # Intent classifier & ranking engine
│   ├── tests/test_api.py               # Pytest automated test suite
│   ├── run_server.py                   # Server starter script
│   └── requirements.txt                # Python dependencies
└── README.md
```

---

## 🚀 Getting Started

### 1. Running the Android Application
1. Open **Android Studio**.
2. Select **Open** and choose the `android` directory.
3. Allow Gradle to sync.
4. Run on your connected Android device or emulator.

### 2. Running the Companion Backend
```powershell
pip install -r backend/requirements.txt
python backend/run_server.py
```
Backend runs on `http://127.0.0.1:8000`. Interactive Swagger API docs are available at `http://127.0.0.1:8000/docs`.

### 3. Running Backend Tests
```powershell
$env:PYTHONPATH="backend"
pytest backend/tests
```
