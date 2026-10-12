package com.projectdora.v1.data.local

import com.projectdora.v1.core.model.AppEntity
import com.projectdora.v1.core.model.AutomationCapability
import com.projectdora.v1.core.model.PricingModel

object CuratedCatalogueData {

    val APPS: List<AppEntity> = listOf(
        // --- AI Research ---
        AppEntity(
            id = "perplexity",
            name = "Perplexity AI",
            category = "AI research",
            officialUrl = "https://www.perplexity.ai",
            packageName = "ai.perplexity.app.android",
            description = "Conversational answer engine combining web index search with verified source citations.",
            bestUseCase = "Broad web research with direct reference citations and real-time facts.",
            suitabilityExplanation = "Ideal for open-ended topical exploration, competitive analysis, and synthesizing diverse web sources with clickable citations.",
            pricingModel = PricingModel.FREEMIUM,
            pricingDetails = "Free tier includes standard Quick searches. Pro tier ($20/mo) adds Claude/GPT-4 models and deep research file analysis.",
            pricingSourceUrl = "https://www.perplexity.ai/pro",
            lastVerifiedDate = "2026-03-01",
            androidAvailable = true,
            knownLimitations = listOf(
                "Free tier limits Pro search runs per 4-hour window.",
                "Deep synthesis across 50+ page PDFs requires Pro plan."
            ),
            automationCapabilities = listOf(
                AutomationCapability.DEEP_LINK_LAUNCH,
                AutomationCapability.WEB_BROWSER_LAUNCH,
                AutomationCapability.CLIPBOARD_QUERY_STAGING,
                AutomationCapability.MANUAL_GUIDANCE
            ),
            tags = listOf("ai research", "web search", "citations", "healthcare", "synthesis", "answers")
        ),
        AppEntity(
            id = "consensus",
            name = "Consensus",
            category = "Academic research",
            officialUrl = "https://consensus.app",
            packageName = null,
            description = "AI search engine designed specifically to extract consensus answers from peer-reviewed scientific papers.",
            bestUseCase = "Finding scientific consensus and evidence-backed answers to biomedical, clinical, and scientific questions.",
            suitabilityExplanation = "Unmatched for evidence synthesis in healthcare and medical research where peer-reviewed verification is essential.",
            pricingModel = PricingModel.FREEMIUM,
            pricingDetails = "Free plan offers unlimited basic searches and limited Consensus Meter credits. Premium is $8.99/mo.",
            pricingSourceUrl = "https://consensus.app/pricing",
            lastVerifiedDate = "2026-03-01",
            androidAvailable = false,
            knownLimitations = listOf(
                "Limited non-academic web coverage.",
                "Consensus meter requires sufficient peer-reviewed publication volume."
            ),
            automationCapabilities = listOf(
                AutomationCapability.WEB_BROWSER_LAUNCH,
                AutomationCapability.CLIPBOARD_QUERY_STAGING,
                AutomationCapability.MANUAL_GUIDANCE
            ),
            tags = listOf("academic research", "clinical", "medicine", "healthcare", "peer-reviewed", "science")
        ),
        AppEntity(
            id = "elicit",
            name = "Elicit",
            category = "Academic research",
            officialUrl = "https://elicit.com",
            packageName = null,
            description = "AI research assistant that automates literature reviews, finding key papers and synthesizing findings across 200M+ papers.",
            bestUseCase = "Structured systematic literature reviews, clinical trial parameter extraction, and meta-analyses.",
            suitabilityExplanation = "Best suited for medical researchers and academics who need tabular summaries of methodology, sample size, and results.",
            pricingModel = PricingModel.FREEMIUM,
            pricingDetails = "Basic tier provides 5,000 one-time research credits. Plus tier is $12/mo for 12,000 monthly credits.",
            pricingSourceUrl = "https://elicit.com/pricing",
            lastVerifiedDate = "2026-03-01",
            androidAvailable = false,
            knownLimitations = listOf(
                "No native Android app (web optimized).",
                "Credit system can deplete quickly on large multi-paper tables."
            ),
            automationCapabilities = listOf(
                AutomationCapability.WEB_BROWSER_LAUNCH,
                AutomationCapability.CLIPBOARD_QUERY_STAGING,
                AutomationCapability.MANUAL_GUIDANCE
            ),
            tags = listOf("academic research", "literature review", "papers", "trials", "healthcare", "meta-analysis")
        ),
        AppEntity(
            id = "chatgpt",
            name = "ChatGPT",
            category = "AI research",
            officialUrl = "https://chatgpt.com",
            packageName = "com.openai.chatgpt",
            description = "Multipurpose conversational foundation model by OpenAI with web browsing, reasoning, and voice mode.",
            bestUseCase = "General concept explanation, brainstorming, draft generation, and iterative problem solving.",
            suitabilityExplanation = "Most versatile all-rounder, excellent for preliminary conceptual mapping before diving into specialized databases.",
            pricingModel = PricingModel.FREEMIUM,
            pricingDetails = "Free access to GPT-4o mini and standard GPT-4o with rate limits. Plus is $20/mo.",
            pricingSourceUrl = "https://openai.com/chatgpt/pricing",
            lastVerifiedDate = "2026-03-01",
            androidAvailable = true,
            knownLimitations = listOf(
                "Web search synthesis may miss specialized paywalled clinical journals.",
                "Occasional hallucinations when asked for obscure specific DOI citations."
            ),
            automationCapabilities = listOf(
                AutomationCapability.DEEP_LINK_LAUNCH,
                AutomationCapability.WEB_BROWSER_LAUNCH,
                AutomationCapability.CLIPBOARD_QUERY_STAGING,
                AutomationCapability.MANUAL_GUIDANCE
            ),
            tags = listOf("ai research", "conversational", "reasoning", "general", "writing", "coding")
        ),
        AppEntity(
            id = "claude",
            name = "Claude",
            category = "AI research",
            officialUrl = "https://claude.ai",
            packageName = "com.anthropic.claude",
            description = "Anthropic's AI assistant renowned for nuance, deep long-context reasoning, and document synthesis.",
            bestUseCase = "Analyzing massive PDFs, complex clinical guidelines, and thoughtful academic synthesis.",
            suitabilityExplanation = "Leading context window (200k tokens) allows uploading entire research papers and books for comprehensive analysis.",
            pricingModel = PricingModel.FREEMIUM,
            pricingDetails = "Free tier gives access to Claude 3.5 Sonnet with dynamic message caps. Pro tier is $20/mo.",
            pricingSourceUrl = "https://claude.ai/pricing",
            lastVerifiedDate = "2026-03-01",
            androidAvailable = true,
            knownLimitations = listOf(
                "Strict rate limits on free tier during peak hours.",
                "No direct live search engine index (relies on uploaded documents or browsing)."
            ),
            automationCapabilities = listOf(
                AutomationCapability.DEEP_LINK_LAUNCH,
                AutomationCapability.WEB_BROWSER_LAUNCH,
                AutomationCapability.CLIPBOARD_QUERY_STAGING,
                AutomationCapability.MANUAL_GUIDANCE
            ),
            tags = listOf("ai research", "long context", "pdf analysis", "nuance", "reasoning", "writing")
        ),

        // --- Academic Research ---
        AppEntity(
            id = "semantic_scholar",
            name = "Semantic Scholar",
            category = "Academic research",
            officialUrl = "https://www.semanticscholar.org",
            packageName = null,
            description = "Free, AI-powered academic search engine developed by the Allen Institute for AI.",
            bestUseCase = "Tracking citation graphs, highly influential citations, and TLDR paper summaries.",
            suitabilityExplanation = "100% free non-profit tool that highlights which papers directly build on or refute prior work.",
            pricingModel = PricingModel.FREE,
            pricingDetails = "Completely free to use for all search and citation features.",
            pricingSourceUrl = "https://www.semanticscholar.org/about",
            lastVerifiedDate = "2026-03-01",
            androidAvailable = false,
            knownLimitations = listOf(
                "Web interface only; no dedicated Android APK.",
                "Requires user to read full papers manually if open-access PDF link is missing."
            ),
            automationCapabilities = listOf(
                AutomationCapability.WEB_BROWSER_LAUNCH,
                AutomationCapability.CLIPBOARD_QUERY_STAGING,
                AutomationCapability.MANUAL_GUIDANCE
            ),
            tags = listOf("academic research", "citations", "free", "papers", "tldr", "science")
        ),
        AppEntity(
            id = "connected_papers",
            name = "Connected Papers",
            category = "Academic research",
            officialUrl = "https://www.connectedpapers.com",
            packageName = null,
            description = "Visual tool to find and explore academic papers related to a single origin paper in a visual graph.",
            bestUseCase = "Visualizing research landscapes and uncovering prior/derivative works.",
            suitabilityExplanation = "Best for building an intuitive mental map of scientific literature clusters.",
            pricingModel = PricingModel.FREEMIUM,
            pricingDetails = "5 free visual graphs per month. Premium tier is $3/mo for unlimited graphs.",
            pricingSourceUrl = "https://www.connectedpapers.com/pricing",
            lastVerifiedDate = "2026-03-01",
            androidAvailable = false,
            knownLimitations = listOf(
                "5 graph monthly limit on free accounts.",
                "Requires an initial seed paper rather than broad text queries."
            ),
            automationCapabilities = listOf(
                AutomationCapability.WEB_BROWSER_LAUNCH,
                AutomationCapability.CLIPBOARD_QUERY_STAGING,
                AutomationCapability.MANUAL_GUIDANCE
            ),
            tags = listOf("academic research", "visual graph", "citations", "literature map")
        ),

        // --- Coding ---
        AppEntity(
            id = "replit",
            name = "Replit",
            category = "Coding",
            officialUrl = "https://replit.com",
            packageName = "com.replit.app",
            description = "Browser & mobile IDE with collaborative coding and AI agent assistant.",
            bestUseCase = "Coding, prototyping, and hosting apps directly from an Android phone or browser.",
            suitabilityExplanation = "One of the few full-featured coding environments with a first-class mobile app.",
            pricingModel = PricingModel.FREEMIUM,
            pricingDetails = "Free starter workspace with basic public repls. Core subscription is $15/mo.",
            pricingSourceUrl = "https://replit.com/pricing",
            lastVerifiedDate = "2026-03-01",
            androidAvailable = true,
            knownLimitations = listOf(
                "Cloud compute resources sleep after inactivity on free tier.",
                "AI Agent workflows consume credits rapidly."
            ),
            automationCapabilities = listOf(
                AutomationCapability.DEEP_LINK_LAUNCH,
                AutomationCapability.WEB_BROWSER_LAUNCH,
                AutomationCapability.CLIPBOARD_QUERY_STAGING,
                AutomationCapability.MANUAL_GUIDANCE
            ),
            tags = listOf("coding", "ide", "mobile dev", "python", "javascript", "hosting")
        ),
        AppEntity(
            id = "phind",
            name = "Phind",
            category = "Coding",
            officialUrl = "https://www.phind.com",
            packageName = null,
            description = "AI search engine tailored specifically for developers and technical problem solving.",
            bestUseCase = "Debugging complex compiler errors, framework setup, and library documentation lookups.",
            suitabilityExplanation = "Generates working code snippets backed by links to official technical documentation.",
            pricingModel = PricingModel.FREEMIUM,
            pricingDetails = "Free access to Phind-70B model. Phind Pro is $20/mo for GPT-4/Claude 3.5 Sonnet pairing.",
            pricingSourceUrl = "https://www.phind.com/pricing",
            lastVerifiedDate = "2026-03-01",
            androidAvailable = false,
            knownLimitations = listOf(
                "Web only, no official Android app.",
                "Not optimized for non-coding queries."
            ),
            automationCapabilities = listOf(
                AutomationCapability.WEB_BROWSER_LAUNCH,
                AutomationCapability.CLIPBOARD_QUERY_STAGING,
                AutomationCapability.MANUAL_GUIDANCE
            ),
            tags = listOf("coding", "developer search", "debugging", "documentation", "code snippets")
        ),
        AppEntity(
            id = "github_mobile",
            name = "GitHub",
            category = "Coding",
            officialUrl = "https://github.com",
            packageName = "com.github.android",
            description = "Official GitHub mobile app for reviewing code, issues, notifications, and repositories.",
            bestUseCase = "Tracking repository progress, triaging issues, and reviewing pull requests on Android.",
            suitabilityExplanation = "Essential companion for any software development team workflow on Android.",
            pricingModel = PricingModel.FREE,
            pricingDetails = "Mobile app is completely free with public and private GitHub accounts.",
            pricingSourceUrl = "https://github.com/pricing",
            lastVerifiedDate = "2026-03-01",
            androidAvailable = true,
            knownLimitations = listOf(
                "Cannot edit multiple code files or run terminal build commands natively on mobile.",
                "Reviewing large diffs requires a tablet or desktop."
            ),
            automationCapabilities = listOf(
                AutomationCapability.DEEP_LINK_LAUNCH,
                AutomationCapability.WEB_BROWSER_LAUNCH,
                AutomationCapability.MANUAL_GUIDANCE
            ),
            tags = listOf("coding", "git", "repositories", "code review", "collaboration")
        ),

        // --- Studying and Education ---
        AppEntity(
            id = "khan_academy",
            name = "Khan Academy",
            category = "Studying and education",
            officialUrl = "https://www.khanacademy.org",
            packageName = "org.khanacademy.android",
            description = "Free non-profit educational platform with video lectures, exercises, and mastery learning.",
            bestUseCase = "Mastering foundational math, science, economics, and exam preparation without cost.",
            suitabilityExplanation = "100% free with no ads, verified curriculum alignment, and high-quality Android tablet/phone support.",
            pricingModel = PricingModel.FREE,
            pricingDetails = "Completely free forever for all students and teachers worldwide.",
            pricingSourceUrl = "https://www.khanacademy.org/about",
            lastVerifiedDate = "2026-03-01",
            androidAvailable = true,
            knownLimitations = listOf(
                "Structured curriculum focuses primarily on K-12 and early college subjects.",
                "Does not feature arbitrary AI document upload."
            ),
            automationCapabilities = listOf(
                AutomationCapability.DEEP_LINK_LAUNCH,
                AutomationCapability.WEB_BROWSER_LAUNCH,
                AutomationCapability.MANUAL_GUIDANCE
            ),
            tags = listOf("education", "studying", "math", "science", "exam prep", "free")
        ),
        AppEntity(
            id = "quizlet",
            name = "Quizlet",
            category = "Studying and education",
            officialUrl = "https://quizlet.com",
            packageName = "com.quizlet.quizletandroid",
            description = "Interactive digital flashcards, practice tests, and spaced repetition study tools.",
            bestUseCase = "Memorization, medical terminology, foreign languages, and exam flashcards.",
            suitabilityExplanation = "Vast community database of pre-made flashcard sets with offline Android support.",
            pricingModel = PricingModel.FREEMIUM,
            pricingDetails = "Free tier includes flashcard practice. Quizlet Plus ($35.99/yr) unlocks AI study guides and ad-free experience.",
            pricingSourceUrl = "https://quizlet.com/upgrade",
            lastVerifiedDate = "2026-03-01",
            androidAvailable = true,
            knownLimitations = listOf(
                "Free version displays advertisements.",
                "Advanced AI test-generation requires annual subscription."
            ),
            automationCapabilities = listOf(
                AutomationCapability.DEEP_LINK_LAUNCH,
                AutomationCapability.WEB_BROWSER_LAUNCH,
                AutomationCapability.MANUAL_GUIDANCE
            ),
            tags = listOf("studying", "flashcards", "memorization", "exams", "spaced repetition")
        ),

        // --- Documents and PDFs ---
        AppEntity(
            id = "chatpdf",
            name = "ChatPDF",
            category = "Documents and PDFs",
            officialUrl = "https://www.chatpdf.com",
            packageName = null,
            description = "Fast AI tool to chat with any PDF file, summarize research articles, and answer questions.",
            bestUseCase = "Instant query and summary over medical papers, financial reports, and legal contracts.",
            suitabilityExplanation = "Zero-setup interface: drop a PDF and immediately ask natural questions with source page citations.",
            pricingModel = PricingModel.FREEMIUM,
            pricingDetails = "Free allows 2 PDFs/day up to 120 pages each. Plus is $5/mo for unlimited PDFs up to 2,000 pages.",
            pricingSourceUrl = "https://www.chatpdf.com/pricing",
            lastVerifiedDate = "2026-03-01",
            androidAvailable = false,
            knownLimitations = listOf(
                "Free tier capped at 2 PDFs per 24 hours.",
                "No native Android app (requires mobile web browser)."
            ),
            automationCapabilities = listOf(
                AutomationCapability.WEB_BROWSER_LAUNCH,
                AutomationCapability.CLIPBOARD_QUERY_STAGING,
                AutomationCapability.MANUAL_GUIDANCE
            ),
            tags = listOf("documents and pdfs", "pdf chat", "summary", "research papers", "contracts")
        ),
        AppEntity(
            id = "adobe_acrobat",
            name = "Adobe Acrobat Reader",
            category = "Documents and PDFs",
            officialUrl = "https://www.adobe.com/acrobat/mobile.html",
            packageName = "com.adobe.reader",
            description = "Industry standard PDF viewer with annotation, signing, and AI assistant summarization.",
            bestUseCase = "Viewing, annotating, signing, and managing official documents on Android devices.",
            suitabilityExplanation = "Most reliable offline PDF rendering and signature workflow on mobile.",
            pricingModel = PricingModel.FREEMIUM,
            pricingDetails = "Free for viewing and basic annotations. Acrobat Pro / AI Assistant add-on starts at $4.99/mo.",
            pricingSourceUrl = "https://www.adobe.com/acrobat/pricing.html",
            lastVerifiedDate = "2026-03-01",
            androidAvailable = true,
            knownLimitations = listOf(
                "AI Assistant features require separate paid add-on.",
                "App size is relatively heavy (>100MB)."
            ),
            automationCapabilities = listOf(
                AutomationCapability.DEEP_LINK_LAUNCH,
                AutomationCapability.WEB_BROWSER_LAUNCH,
                AutomationCapability.MANUAL_GUIDANCE
            ),
            tags = listOf("documents and pdfs", "pdf viewer", "annotations", "signing", "official")
        ),

        // --- Writing ---
        AppEntity(
            id = "grammarly",
            name = "Grammarly",
            category = "Writing",
            officialUrl = "https://www.grammarly.com",
            packageName = "com.grammarly.android.keyboard",
            description = "AI writing assistant offering grammar checks, clarity suggestions, and tone adjustment.",
            bestUseCase = "Polishing emails, academic papers, and professional messages directly on mobile keyboard.",
            suitabilityExplanation = "Integrates as an Android system keyboard to check text across all apps seamlessly.",
            pricingModel = PricingModel.FREEMIUM,
            pricingDetails = "Free tier includes spelling, grammar, and punctuation. Premium starts at $12/mo.",
            pricingSourceUrl = "https://www.grammarly.com/plans",
            lastVerifiedDate = "2026-03-01",
            androidAvailable = true,
            knownLimitations = listOf(
                "Advanced clarity and vocabulary enhancements require Premium.",
                "Requires keyboard input method permissions."
            ),
            automationCapabilities = listOf(
                AutomationCapability.DEEP_LINK_LAUNCH,
                AutomationCapability.WEB_BROWSER_LAUNCH,
                AutomationCapability.MANUAL_GUIDANCE
            ),
            tags = listOf("writing", "grammar", "keyboard", "editing", "tone", "proofreading")
        ),
        AppEntity(
            id = "quillbot",
            name = "QuillBot",
            category = "Writing",
            officialUrl = "https://quillbot.com",
            packageName = "com.quillbot.android",
            description = "AI paraphrasing, summarization, and citation generation tool for writers and students.",
            bestUseCase = "Rewording sentences, avoiding redundancy, and generating APA/MLA citations.",
            suitabilityExplanation = "Offers multiple writing modes (Standard, Fluency, Formal) with interactive thesaurus.",
            pricingModel = PricingModel.FREEMIUM,
            pricingDetails = "Free paraphrases up to 125 words at a time. Premium is $9.95/mo for unlimited words.",
            pricingSourceUrl = "https://quillbot.com/upgrade",
            lastVerifiedDate = "2026-03-01",
            androidAvailable = true,
            knownLimitations = listOf(
                "Word limit on free paraphrasing prompts.",
                "Advanced tone modes locked behind subscription."
            ),
            automationCapabilities = listOf(
                AutomationCapability.DEEP_LINK_LAUNCH,
                AutomationCapability.WEB_BROWSER_LAUNCH,
                AutomationCapability.CLIPBOARD_QUERY_STAGING,
                AutomationCapability.MANUAL_GUIDANCE
            ),
            tags = listOf("writing", "paraphrase", "citations", "academic writing", "summary")
        ),

        // --- Image and Video Creation ---
        AppEntity(
            id = "canva",
            name = "Canva",
            category = "Image and video creation",
            officialUrl = "https://www.canva.com",
            packageName = "com.canva.editor",
            description = "Comprehensive graphic design, presentation, and video editing tool with AI Magic Studio.",
            bestUseCase = "Creating social media graphics, posters, presentations, and thumbnails quickly.",
            suitabilityExplanation = "Massive template library combined with intuitive mobile touch controls and free export.",
            pricingModel = PricingModel.FREEMIUM,
            pricingDetails = "Free tier includes thousands of templates and cloud export. Canva Pro is $12.99/mo.",
            pricingSourceUrl = "https://www.canva.com/pricing",
            lastVerifiedDate = "2026-03-01",
            androidAvailable = true,
            knownLimitations = listOf(
                "Premium photos, background remover, and Magic Switch require Pro.",
                "Requires stable internet connection for template assets."
            ),
            automationCapabilities = listOf(
                AutomationCapability.DEEP_LINK_LAUNCH,
                AutomationCapability.WEB_BROWSER_LAUNCH,
                AutomationCapability.MANUAL_GUIDANCE
            ),
            tags = listOf("image and video creation", "graphic design", "presentations", "templates", "social media")
        ),
        AppEntity(
            id = "capcut",
            name = "CapCut",
            category = "Image and video creation",
            officialUrl = "https://www.capcut.com",
            packageName = "com.lemon.lvoverseas",
            description = "All-in-one video editing app featuring auto-captions, trendy transitions, and AI video filters.",
            bestUseCase = "Editing short-form TikTok, Reels, and YouTube Shorts videos directly on phone.",
            suitabilityExplanation = "Exceptional mobile video editing speed with accurate speech-to-caption generation.",
            pricingModel = PricingModel.FREEMIUM,
            pricingDetails = "Free tier includes timeline editing and standard exports. CapCut Pro is $9.99/mo.",
            pricingSourceUrl = "https://www.capcut.com/pricing",
            lastVerifiedDate = "2026-03-01",
            androidAvailable = true,
            knownLimitations = listOf(
                "Certain viral filters and 4K cloud exports require Pro.",
                "Availability varies by regional app stores."
            ),
            automationCapabilities = listOf(
                AutomationCapability.DEEP_LINK_LAUNCH,
                AutomationCapability.WEB_BROWSER_LAUNCH,
                AutomationCapability.MANUAL_GUIDANCE
            ),
            tags = listOf("image and video creation", "video editing", "reels", "shorts", "captions", "filters")
        ),

        // --- Productivity ---
        AppEntity(
            id = "notion",
            name = "Notion",
            category = "Productivity",
            officialUrl = "https://www.notion.so",
            packageName = "notion.id",
            description = "Connected workspace for notes, docs, databases, project management, and AI synthesis.",
            bestUseCase = "Organizing research notes, project roadmaps, study wikis, and knowledge bases.",
            suitabilityExplanation = "Hyper-flexible relational databases and markdown notes sync seamlessly between Android and web.",
            pricingModel = PricingModel.FREEMIUM,
            pricingDetails = "Free for individuals with unlimited pages. Plus is $10/mo; Notion AI is $10/mo add-on.",
            pricingSourceUrl = "https://www.notion.so/pricing",
            lastVerifiedDate = "2026-03-01",
            androidAvailable = true,
            knownLimitations = listOf(
                "Complex nested databases can be slow to scroll on older Android devices.",
                "Offline mode is limited to recently opened pages."
            ),
            automationCapabilities = listOf(
                AutomationCapability.DEEP_LINK_LAUNCH,
                AutomationCapability.WEB_BROWSER_LAUNCH,
                AutomationCapability.CLIPBOARD_QUERY_STAGING,
                AutomationCapability.MANUAL_GUIDANCE
            ),
            tags = listOf("productivity", "notes", "knowledge base", "databases", "collaboration", "wiki")
        ),
        AppEntity(
            id = "todoist",
            name = "Todoist",
            category = "Productivity",
            officialUrl = "https://todoist.com",
            packageName = "com.todoist",
            description = "Task manager and to-do list with natural language date parsing and productivity karma.",
            bestUseCase = "Daily task organization, habit tracking, and project deadline management.",
            suitabilityExplanation = "Best natural language parsing: typing 'Buy books every Friday at 5pm' schedules automatically.",
            pricingModel = PricingModel.FREEMIUM,
            pricingDetails = "Free allows up to 5 active projects. Pro is $4/mo for 300 projects and reminders.",
            pricingSourceUrl = "https://todoist.com/pricing",
            lastVerifiedDate = "2026-03-01",
            androidAvailable = true,
            knownLimitations = listOf(
                "Task reminders require paid Pro tier.",
                "Activity log history on free plan is limited to 1 week."
            ),
            automationCapabilities = listOf(
                AutomationCapability.DEEP_LINK_LAUNCH,
                AutomationCapability.WEB_BROWSER_LAUNCH,
                AutomationCapability.MANUAL_GUIDANCE
            ),
            tags = listOf("productivity", "task manager", "todo", "habits", "reminders")
        )
    )

    fun findById(id: String): AppEntity? {
        return APPS.firstOrNull { it.id.equals(id, ignoreCase = true) }
    }
}
