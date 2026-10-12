# PROJECT DORA V1 — MASTER BUILD PROMPT
## A Futuristic Voice-First AI Companion for App Discovery and Guided Automation

Act as a senior Android developer, AI engineer, product designer, backend architect, and QA engineer.

Build Version 1 of Project Dora: a futuristic, voice-first Android assistant inspired by the idea of a personal companion who can find the right digital tool for any genuine user request.

The user should be able to speak naturally, explain what they want to accomplish, receive a shortlist of relevant applications, understand which one is best for their requirements, open their selected application, and get help using it.

This is the first working product release, not a concept demo.

Do not attempt to build universal automation for every application. Build a polished, reliable foundation that can expand into a broader personal AI assistant over time.

# 1. PRODUCT VISION

The user should not need to know which application to use.

They should be able to say:

"Dora, mujhe AI ki help se research karni hai."

The assistant should:
1. Understand the user's intent.
2. Discover relevant applications.
3. Compare the strongest options.
4. Explain why one option suits the task.
5. Show pricing information and limitations.
6. Let the user choose by voice or touch.
7. Open the selected official website or installed application.
8. Automate only those actions that are supported and authorized.
9. Guide the user when automation is unavailable.
10. Continue the conversation with the existing task context.

The core principle is:

"Tell Dora what you want to accomplish. Dora helps you find and use the right tools."

# 2. TARGET PLATFORM AND TECHNOLOGY

Build a native Android application.

Preferred stack:
- Kotlin.
- Jetpack Compose.
- Material 3 components.
- Kotlin coroutines and Flow.
- Android speech recognition APIs where suitable.
- Android TextToSpeech for spoken responses.
- Android intents and verified deep links.
- A modular architecture separating UI, domain logic and data access.
- Python and FastAPI for the backend if live AI and app discovery require it.
- PostgreSQL for persistent application catalogue data when a backend is used.

Use a provider abstraction for AI, speech recognition and speech synthesis so that services can be changed later.

If credentials are unavailable, provide a clearly labeled demo mode with functional local interactions and deterministic sample responses.

Do not expose API keys in the Android application.

Before implementing the project, inspect the existing repository. Reuse working code where appropriate and document any architectural changes.

# 3. VISUAL DESIGN DIRECTION

Create an original, premium, futuristic AI companion interface.

Visual identity:
- Deep navy background.
- Electric blue and cyan accents.
- Subtle gradients.
- Soft, restrained glow effects.
- Rounded cards.
- Clean typography.
- Smooth transitions.
- Minimal clutter.
- Strong readability and accessible contrast.
- Responsive layouts for different Android screen sizes.

The application should feel like a personal AI companion, not a conventional search engine.

Create an original abstract robot companion, animated orb, or futuristic assistant avatar. Do not copy Doraemon's protected character artwork, exact character design, logo or branding.

Animations should communicate actual states:
- Idle.
- Listening.
- Thinking.
- Presenting recommendations.
- Executing an action.
- Speaking.
- Error or recovery.

Do not add animations that make the application feel slow or distract users from their tasks.

# 4. MAIN NAVIGATION

Build four primary destinations:

1. Home
2. Discover
3. Activity
4. Settings

Use a consistent navigation system and preserve relevant state when switching screens.

## SCREEN A — HOME

This is the primary experience.

Include:
- Project Dora branding.
- A friendly assistant avatar or animated voice orb.
- A natural greeting.
- A large microphone control.
- A text input alternative.
- A clear listening indicator.
- Example requests.
- Recently used applications.
- Recent task summaries.
- A visible cancel or stop control during active operations.

Example greeting:

"Hey! Main Dora hoon. Batao, aaj kya karna hai?"

Example suggestion chips:
- Find an AI research tool.
- Help me study for an exam.
- Find free video editing apps.
- Explain a PDF.
- Help me build a website.

Do not require the user to select a category before asking a question.

## SCREEN B — DISCOVERY RESULTS

Display relevant application recommendations as cards.

Each card should include:
- Application name.
- Logo or fallback icon.
- Short description.
- Best use case.
- Suitability explanation.
- Free or paid status.
- Important limitations.
- Official website.
- Android availability when verified.
- Automation capability status.
- Open button.
- Compare option.

Display two to five relevant options by default.

Provide sorting or filtering options such as:
- Best match.
- Free options.
- Beginner-friendly.
- Android apps.
- Web apps.

Do not invent ratings, pricing, application features, or verification status.

If information is unknown, label it as unknown.

Clearly distinguish a verified free tier from a feature that requires payment.

## SCREEN C — APPLICATION COMPARISON

Allow the user to compare selected applications side by side or in stacked cards suitable for mobile screens.

Compare:
- Task suitability.
- Key features.
- Free-tier restrictions.
- Paid features.
- Ease of use.
- Platform availability.
- Privacy considerations.
- Automation capabilities.
- Official pricing links.
- Data verification dates.

At the top, provide a clear recommendation with a concise explanation.

Example:

"For general web research, Option A is my recommendation. Option B may suit you better if your priority is academic literature."

The recommendation must be based on available evidence, not arbitrary scores.

## SCREEN D — TASK WORKSPACE

After the user selects an application, open a task workspace that displays:
- The user's original request.
- Selected application.
- Current task step.
- Action status.
- Next available action.
- Guidance when manual interaction is required.
- Cancel or retry controls.

For V1, support:
- Opening verified official URLs.
- Launching installed applications where their launch targets are verified.
- Maintaining task context.
- Copying a user-approved query when appropriate.
- Guided interaction when automatic text entry or result retrieval is unsupported.

Do not claim that entering a query, reading a result or completing a workflow succeeded unless the application can verify that outcome.

## SCREEN E — ACTIVITY

Show previous tasks with:
- Original request.
- Selected application.
- Date.
- Final status.
- Actions completed.
- Any manual steps required.

Allow users to reopen a saved task or clear their history.

Persist task history only according to the user's privacy settings.

## SCREEN F — SETTINGS

Include:
- Language preference.
- Spoken response toggle.
- Speech speed, where supported.
- Theme preference.
- Saved history controls.
- Data deletion.
- Connected service settings if implemented.
- Permission explanations.
- Automation settings.
- App information.
- Demo mode indicator where relevant.

Never ask for accessibility permission during initial onboarding unless a feature genuinely requires it and the reason has been explained.

# 5. VOICE INTERACTION

Implement voice input and spoken output.

Support English and Hinglish to the extent supported by the selected speech services.

The user must be able to:
- Start a voice request.
- See the recognized transcript.
- Correct a misunderstood transcript.
- Receive spoken responses.
- Interrupt or cancel an active task.
- Switch to text input.
- Refer to earlier recommendations by position or name.

Example:

User: "Dora, mujhe research ke liye best AI tools bata."

Assistant: "Mujhe kuch useful options mile hain. Kya tum general web research karna chahte ho ya academic papers par focus karna hai?"

Ask a clarifying question only when it materially improves the recommendation. Otherwise, use reasonable defaults.

Do not implement unrestricted always-listening behavior in V1. Start with a visible microphone control and supported speech APIs.

Handle denied microphone permission gracefully.

# 6. APP DISCOVERY ENGINE

Build an application registry that supports a curated catalogue and additional live discovery.

Start with these categories:
- AI research.
- Academic research.
- Studying and education.
- Coding.
- Writing.
- Image and video creation.
- Productivity.
- Documents and PDFs.

Start with approximately 20 to 50 curated applications, but only include entries with sufficiently reliable metadata. The exact number should depend on available verified data.

Each entry should contain:
- ID.
- Name.
- Category.
- Official URL.
- Description.
- Supported tasks.
- Pricing information.
- Source URL for pricing.
- Last verification date.
- Android availability.
- Known limitations.
- Automation capabilities.
- Confidence and missing-information indicators.

Support searching the registry and ranking applications by relevance.

Add live web discovery through a permitted search provider when available.

Deduplicate results, validate official destinations, and avoid showing multiple entries for the same product.

If live search is unavailable, explain that the results come from the current catalogue rather than pretending a live search occurred.

Do not attempt to catalogue the entire internet in V1.

# 7. RECOMMENDATION ENGINE

For every request:
1. Extract the user's intent.
2. Identify constraints.
3. Find matching applications.
4. Rank candidates.
5. Explain why the top candidates fit.
6. Surface meaningful cost and capability differences.
7. Identify what can be automated.
8. Let the user choose.

Use a structured response schema validated by the application.

The AI must not generate arbitrary application records and present them as verified facts.

Use evidence and confidence indicators when comparing pricing or capabilities.

Never present a recommendation score as an objective measurement unless its methodology is defined.

# 8. ANDROID APP OPENING

Implement a safe app-launching layer.

It should:
- Open verified HTTPS URLs.
- Launch installed apps when a verified package and supported launch mechanism exist.
- Use verified deep links when available.
- Detect missing apps when feasible.
- Offer the official website as a fallback.
- Handle invalid URLs and launch failures.
- Preserve the original task context.

Do not invent package names or deep links.

Basic discovery and app launching must not require a partnership with the application being recommended.

# 9. AUTOMATION IN V1

Implement a capability-driven action engine.

Supported V1 actions should include:
- Search applications.
- Compare applications.
- Open a verified URL.
- Launch a supported installed application.
- Prepare and copy a user-approved query.
- Speak a response.
- Display task status.
- Cancel a task.
- Provide guided next steps.

Treat accessibility-based UI automation as an optional, separate capability to investigate and add only after confirming technical feasibility, permission requirements and applicable distribution policies.

Do not promise universal tapping, typing, or result extraction in the initial release.

For each action, record:
- Preconditions.
- Requested action.
- Execution method.
- Observable success criteria.
- Actual result.
- Error state.
- Recovery instructions.

The assistant must distinguish between:
- Requested.
- Attempted.
- Completed.
- Verified.

A successful URL launch does not prove that a research task is complete.

If automatic interaction is unavailable, say what happened and guide the user.

# 10. SAFETY AND PERMISSIONS

Build security and privacy into the application from the beginning.

Requirements:
- Explain microphone permissions.
- Use HTTPS for external destinations.
- Validate external URLs.
- Never expose API credentials.
- Never execute arbitrary model-generated code with device privileges.
- Require confirmation before consequential external actions.
- Avoid collecting unrelated screen content.
- Provide cancellation.
- Respect operating-system restrictions.
- Provide history deletion and privacy controls.
- Treat external webpage content as untrusted input.

Any future AccessibilityService must have clear user-facing disclosure, explicit enablement, limited scope, and compliance with applicable Android and Google Play policies.

# 11. BACKEND AND DATA MODEL

Use a modular backend only where it adds real value.

Recommended backend responsibilities:
- AI conversation and intent analysis.
- Application search.
- Recommendation ranking.
- Pricing metadata.
- Source verification records.
- Catalogue updates.
- Optional account synchronization.

The Android device remains responsible for local device actions.

Suggested data entities:
- Application.
- ApplicationCapability.
- PricingEvidence.
- DiscoverySource.
- UserPreference.
- Conversation.
- Task.
- TaskStep.
- ExecutionEvent.

Use PostgreSQL for persistent shared data when required.

Provide local development and demo modes so the UI and core user journey can be tested without external credentials.

Do not introduce unnecessary infrastructure merely to make the architecture look sophisticated.

# 12. FIRST END-TO-END DEMONSTRATION

The first demonstrable workflow must be:

1. User opens Dora.
2. User says: "Find me an AI tool for researching AI in healthcare."
3. Dora returns a relevant shortlist.
4. Dora explains which tool suits the request best.
5. User says: "Open Perplexity."
6. Dora opens a verified official destination.
7. Dora tells the user which interactions it can and cannot perform.
8. Dora helps prepare the research query.
9. If a supported execution mechanism is available, it submits the query and verifies the result.
10. Otherwise, it guides the user through the required interaction.
11. If result content becomes accessible through a supported method, Dora summarizes it.
12. Dora speaks the summary when speech output is enabled.
13. The task status is recorded accurately.

Do not hardcode Perplexity as the only application. It is a demonstration of the workflow, not a special integration requirement.

# 13. DEVELOPMENT PROCESS

Work in the following order:

Phase 1: Inspect the repository and document the architecture.

Phase 2: Build the Android UI, navigation and design system.

Phase 3: Implement voice input, text input and spoken responses.

Phase 4: Implement the application registry, search and recommendation cards.

Phase 5: Implement comparison and official-link launching.

Phase 6: Implement task state, action verification, cancellation and guided fallback.

Phase 7: Complete the end-to-end research workflow.

Phase 8: Test on an actual Android device and fix reproducible issues.

Phase 9: Document limitations, setup and deployment.

After every phase, ensure the project remains runnable.

Do not stop at generating code snippets or attractive static screens.

# 14. ACCEPTANCE CRITERIA

V1 is ready for its initial test when:

- A user can enter a request through voice or text.
- The application can recommend relevant tools from its catalogue.
- Recommendations explain their suitability.
- Pricing uncertainty is represented honestly.
- Official links can be opened safely.
- Comparison works on mobile screens.
- The selected task retains its context.
- Unsupported automation produces a helpful fallback.
- Cancellation works for supported active operations.
- Action status reflects actual observable results.
- History and privacy settings work as described.
- The main journey has automated tests and has been tested on a real Android device.
- Missing API credentials do not prevent local demo mode.
- No core button is merely decorative.
- No unimplemented feature is presented as complete.

Report all unsupported capabilities explicitly.

# 15. FINAL INSTRUCTION

Build Project Dora V1 as a reliable foundation for a larger universal assistant.

Prioritize:
1. Correct recommendations.
2. Clear explanations.
3. Fast, intuitive voice interaction.
4. Reliable application launching.
5. Honest execution reporting.
6. Safe automation and guided recovery.
7. A polished futuristic interface.

Do not sacrifice reliability to claim support for more applications.

The user should experience the magic of having a personal AI companion while the implementation remains transparent, testable and technically honest.

Start with the repository inspection and a concise implementation plan, then build the application in phases. Deliver working code, tests, setup instructions and a precise list of implemented versus incomplete capabilities.