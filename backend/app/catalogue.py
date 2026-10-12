from typing import List, Optional
from app.models import AppEntity, PricingModel, AutomationCapability

CURATED_CATALOGUE: List[AppEntity] = [
    # --- AI Research ---
    AppEntity(
        id="perplexity",
        name="Perplexity AI",
        category="AI research",
        official_url="https://www.perplexity.ai",
        package_name="ai.perplexity.app.android",
        description="Conversational answer engine combining web index search with verified source citations.",
        best_use_case="Broad web research with direct reference citations and real-time facts.",
        suitability_explanation="Ideal for open-ended topical exploration, competitive analysis, and synthesizing diverse web sources with clickable citations.",
        pricing_model=PricingModel.FREEMIUM,
        pricing_details="Free tier includes standard Quick searches. Pro tier ($20/mo) adds Claude/GPT-4 models and deep research file analysis.",
        pricing_source_url="https://www.perplexity.ai/pro",
        last_verified_date="2026-03-01",
        android_available=True,
        known_limitations=[
            "Free tier limits Pro search runs per 4-hour window.",
            "Deep synthesis across 50+ page PDFs requires Pro plan."
        ],
        automation_capabilities=[
            AutomationCapability.DEEP_LINK_LAUNCH,
            AutomationCapability.WEB_BROWSER_LAUNCH,
            AutomationCapability.CLIPBOARD_QUERY_STAGING,
            AutomationCapability.MANUAL_GUIDANCE
        ],
        tags=["ai research", "web search", "citations", "healthcare", "synthesis", "answers"]
    ),
    AppEntity(
        id="consensus",
        name="Consensus",
        category="Academic research",
        official_url="https://consensus.app",
        package_name=None,
        description="AI search engine designed specifically to extract consensus answers from peer-reviewed scientific papers.",
        best_use_case="Finding scientific consensus and evidence-backed answers to biomedical, clinical, and scientific questions.",
        suitability_explanation="Unmatched for evidence synthesis in healthcare and medical research where peer-reviewed verification is essential.",
        pricing_model=PricingModel.FREEMIUM,
        pricing_details="Free plan offers unlimited basic searches and limited Consensus Meter credits. Premium is $8.99/mo.",
        pricing_source_url="https://consensus.app/pricing",
        last_verified_date="2026-03-01",
        android_available=False,
        known_limitations=[
            "Limited non-academic web coverage.",
            "Consensus meter requires sufficient peer-reviewed publication volume."
        ],
        automation_capabilities=[
            AutomationCapability.WEB_BROWSER_LAUNCH,
            AutomationCapability.CLIPBOARD_QUERY_STAGING,
            AutomationCapability.MANUAL_GUIDANCE
        ],
        tags=["academic research", "clinical", "medicine", "healthcare", "peer-reviewed", "science"]
    ),
    AppEntity(
        id="elicit",
        name="Elicit",
        category="Academic research",
        official_url="https://elicit.com",
        package_name=None,
        description="AI research assistant that automates literature reviews, finding key papers and synthesizing findings across 200M+ papers.",
        best_use_case="Structured systematic literature reviews, clinical trial parameter extraction, and meta-analyses.",
        suitability_explanation="Best suited for medical researchers and academics who need tabular summaries of methodology, sample size, and results.",
        pricing_model=PricingModel.FREEMIUM,
        pricing_details="Basic tier provides 5,000 one-time research credits. Plus tier is $12/mo for 12,000 monthly credits.",
        pricing_source_url="https://elicit.com/pricing",
        last_verified_date="2026-03-01",
        android_available=False,
        known_limitations=[
            "No native Android app (web optimized).",
            "Credit system can deplete quickly on large multi-paper tables."
        ],
        automation_capabilities=[
            AutomationCapability.WEB_BROWSER_LAUNCH,
            AutomationCapability.CLIPBOARD_QUERY_STAGING,
            AutomationCapability.MANUAL_GUIDANCE
        ],
        tags=["academic research", "literature review", "papers", "trials", "healthcare", "meta-analysis"]
    ),
    AppEntity(
        id="chatgpt",
        name="ChatGPT",
        category="AI research",
        official_url="https://chatgpt.com",
        package_name="com.openai.chatgpt",
        description="Multipurpose conversational foundation model by OpenAI with web browsing, reasoning, and voice mode.",
        best_use_case="General concept explanation, brainstorming, draft generation, and iterative problem solving.",
        suitability_explanation="Most versatile all-rounder, excellent for preliminary conceptual mapping before diving into specialized databases.",
        pricing_model=PricingModel.FREEMIUM,
        pricing_details="Free access to GPT-4o mini and standard GPT-4o with rate limits. Plus is $20/mo.",
        pricing_source_url="https://openai.com/chatgpt/pricing",
        last_verified_date="2026-03-01",
        android_available=True,
        known_limitations=[
            "Web search synthesis may miss specialized paywalled clinical journals.",
            "Occasional hallucinations when asked for obscure specific DOI citations."
        ],
        automation_capabilities=[
            AutomationCapability.DEEP_LINK_LAUNCH,
            AutomationCapability.WEB_BROWSER_LAUNCH,
            AutomationCapability.CLIPBOARD_QUERY_STAGING,
            AutomationCapability.MANUAL_GUIDANCE
        ],
        tags=["ai research", "conversational", "reasoning", "general", "writing", "coding"]
    ),
    AppEntity(
        id="claude",
        name="Claude",
        category="AI research",
        official_url="https://claude.ai",
        package_name="com.anthropic.claude",
        description="Anthropic's AI assistant renowned for nuance, deep long-context reasoning, and nuanced document synthesis.",
        best_use_case="Analyzing massive PDFs, complex clinical guidelines, and thoughtful academic synthesis.",
        suitability_explanation="Leading context window (200k tokens) allows uploading entire research papers and books for comprehensive analysis.",
        pricing_model=PricingModel.FREEMIUM,
        pricing_details="Free tier gives access to Claude 3.5 Sonnet with dynamic message caps. Pro tier is $20/mo.",
        pricing_source_url="https://claude.ai/pricing",
        last_verified_date="2026-03-01",
        android_available=True,
        known_limitations=[
            "Strict rate limits on free tier during peak hours.",
            "No direct live search engine index (relies on uploaded documents or browsing)."
        ],
        automation_capabilities=[
            AutomationCapability.DEEP_LINK_LAUNCH,
            AutomationCapability.WEB_BROWSER_LAUNCH,
            AutomationCapability.CLIPBOARD_QUERY_STAGING,
            AutomationCapability.MANUAL_GUIDANCE
        ],
        tags=["ai research", "long context", "pdf analysis", "nuance", "reasoning", "writing"]
    ),

    # --- Academic Research ---
    AppEntity(
        id="semantic_scholar",
        name="Semantic Scholar",
        category="Academic research",
        official_url="https://www.semanticscholar.org",
        package_name=None,
        description="Free, AI-powered academic search engine developed by the Allen Institute for AI.",
        best_use_case="Tracking citation graphs, highly influential citations, and TLDR paper summaries.",
        suitability_explanation="100% free non-profit tool that highlights which papers directly build on or refute prior work.",
        pricing_model=PricingModel.FREE,
        pricing_details="Completely free to use for all search and citation features.",
        pricing_source_url="https://www.semanticscholar.org/about",
        last_verified_date="2026-03-01",
        android_available=False,
        known_limitations=[
            "Web interface only; no dedicated Android APK.",
            "Requires user to read full papers manually if open-access PDF link is missing."
        ],
        automation_capabilities=[
            AutomationCapability.WEB_BROWSER_LAUNCH,
            AutomationCapability.CLIPBOARD_QUERY_STAGING,
            AutomationCapability.MANUAL_GUIDANCE
        ],
        tags=["academic research", "citations", "free", "papers", "tldr", "science"]
    ),
    AppEntity(
        id="connected_papers",
        name="Connected Papers",
        category="Academic research",
        official_url="https://www.connectedpapers.com",
        package_name=None,
        description="Visual tool to find and explore academic papers related to a single origin paper in a visual graph.",
        best_use_case="Visualizing research landscapes and uncovering prior/derivative works.",
        suitability_explanation="Best for building an intuitive mental map of scientific literature clusters.",
        pricing_model=PricingModel.FREEMIUM,
        pricing_details="5 free visual graphs per month. Premium tier is $3/mo for unlimited graphs.",
        pricing_source_url="https://www.connectedpapers.com/pricing",
        last_verified_date="2026-03-01",
        android_available=False,
        known_limitations=[
            "5 graph monthly limit on free accounts.",
            "Requires an initial seed paper rather than broad text queries."
        ],
        automation_capabilities=[
            AutomationCapability.WEB_BROWSER_LAUNCH,
            AutomationCapability.CLIPBOARD_QUERY_STAGING,
            AutomationCapability.MANUAL_GUIDANCE
        ],
        tags=["academic research", "visual graph", "citations", "literature map"]
    ),

    # --- Coding ---
    AppEntity(
        id="replit",
        name="Replit",
        category="Coding",
        official_url="https://replit.com",
        package_name="com.replit.app",
        description="Browser & mobile IDE with collaborative coding and AI agent assistant.",
        best_use_case="Coding, prototyping, and hosting apps directly from an Android phone or browser.",
        suitability_explanation="One of the few full-featured coding environments with a first-class mobile app.",
        pricing_model=PricingModel.FREEMIUM,
        pricing_details="Free starter workspace with basic public repls. Core subscription is $15/mo.",
        pricing_source_url="https://replit.com/pricing",
        last_verified_date="2026-03-01",
        android_available=True,
        known_limitations=[
            "Cloud compute resources sleep after inactivity on free tier.",
            "AI Agent workflows consume credits rapidly."
        ],
        automation_capabilities=[
            AutomationCapability.DEEP_LINK_LAUNCH,
            AutomationCapability.WEB_BROWSER_LAUNCH,
            AutomationCapability.CLIPBOARD_QUERY_STAGING,
            AutomationCapability.MANUAL_GUIDANCE
        ],
        tags=["coding", "ide", "mobile dev", "python", "javascript", "hosting"]
    ),
    AppEntity(
        id="phind",
        name="Phind",
        category="Coding",
        official_url="https://www.phind.com",
        package_name=None,
        description="AI search engine tailored specifically for developers and technical problem solving.",
        best_use_case="Debugging complex compiler errors, framework setup, and library documentation lookups.",
        suitability_explanation="Generates working code snippets backed by links to official technical documentation.",
        pricing_model=PricingModel.FREEMIUM,
        pricing_details="Free access to Phind-70B model. Phind Pro is $20/mo for GPT-4/Claude 3.5 Sonnet pairing.",
        pricing_source_url="https://www.phind.com/pricing",
        last_verified_date="2026-03-01",
        android_available=False,
        known_limitations=[
            "Web only, no official Android app.",
            "Not optimized for non-coding queries."
        ],
        automation_capabilities=[
            AutomationCapability.WEB_BROWSER_LAUNCH,
            AutomationCapability.CLIPBOARD_QUERY_STAGING,
            AutomationCapability.MANUAL_GUIDANCE
        ],
        tags=["coding", "developer search", "debugging", "documentation", "code snippets"]
    ),
    AppEntity(
        id="github_mobile",
        name="GitHub",
        category="Coding",
        official_url="https://github.com",
        package_name="com.github.android",
        description="Official GitHub mobile app for reviewing code, issues, notifications, and repositories.",
        best_use_case="Tracking repository progress, triaging issues, and reviewing pull requests on Android.",
        suitability_explanation="Essential companion for any software development team workflow on Android.",
        pricing_model=PricingModel.FREE,
        pricing_details="Mobile app is completely free with public and private GitHub accounts.",
        pricing_source_url="https://github.com/pricing",
        last_verified_date="2026-03-01",
        android_available=True,
        known_limitations=[
            "Cannot edit multiple code files or run terminal build commands natively on mobile.",
            "Reviewing large diffs requires a tablet or desktop."
        ],
        automation_capabilities=[
            AutomationCapability.DEEP_LINK_LAUNCH,
            AutomationCapability.WEB_BROWSER_LAUNCH,
            AutomationCapability.MANUAL_GUIDANCE
        ],
        tags=["coding", "git", "repositories", "code review", "collaboration"]
    ),

    # --- Studying and Education ---
    AppEntity(
        id="khan_academy",
        name="Khan Academy",
        category="Studying and education",
        official_url="https://www.khanacademy.org",
        package_name="org.khanacademy.android",
        description="Free non-profit educational platform with video lectures, exercises, and mastery learning.",
        best_use_case="Mastering foundational math, science, economics, and exam preparation without cost.",
        suitability_explanation="100% free with no ads, verified curriculum alignment, and high-quality Android tablet/phone support.",
        pricing_model=PricingModel.FREE,
        pricing_details="Completely free forever for all students and teachers worldwide.",
        pricing_source_url="https://www.khanacademy.org/about",
        last_verified_date="2026-03-01",
        android_available=True,
        known_limitations=[
            "Structured curriculum focuses primarily on K-12 and early college subjects.",
            "Does not feature arbitrary AI document upload."
        ],
        automation_capabilities=[
            AutomationCapability.DEEP_LINK_LAUNCH,
            AutomationCapability.WEB_BROWSER_LAUNCH,
            AutomationCapability.MANUAL_GUIDANCE
        ],
        tags=["education", "studying", "math", "science", "exam prep", "free"]
    ),
    AppEntity(
        id="quizlet",
        name="Quizlet",
        category="Studying and education",
        official_url="https://quizlet.com",
        package_name="com.quizlet.quizletandroid",
        description="Interactive digital flashcards, practice tests, and spaced repetition study tools.",
        best_use_case="Memorization, medical terminology, foreign languages, and exam flashcards.",
        suitability_explanation="Vast community database of pre-made flashcard sets with offline Android support.",
        pricing_model=PricingModel.FREEMIUM,
        pricing_details="Free tier includes flashcard practice. Quizlet Plus ($35.99/yr) unlocks AI study guides and ad-free experience.",
        pricing_source_url="https://quizlet.com/upgrade",
        last_verified_date="2026-03-01",
        android_available=True,
        known_limitations=[
            "Free version displays advertisements.",
            "Advanced AI test-generation requires annual subscription."
        ],
        automation_capabilities=[
            AutomationCapability.DEEP_LINK_LAUNCH,
            AutomationCapability.WEB_BROWSER_LAUNCH,
            AutomationCapability.MANUAL_GUIDANCE
        ],
        tags=["studying", "flashcards", "memorization", "exams", "spaced repetition"]
    ),

    # --- Documents and PDFs ---
    AppEntity(
        id="chatpdf",
        name="ChatPDF",
        category="Documents and PDFs",
        official_url="https://www.chatpdf.com",
        package_name=None,
        description="Fast AI tool to chat with any PDF file, summarize research articles, and answer questions.",
        best_use_case="Instant query and summary over medical papers, financial reports, and legal contracts.",
        suitability_explanation="Zero-setup interface: drop a PDF and immediately ask natural questions with source page citations.",
        pricing_model=PricingModel.FREEMIUM,
        pricing_details="Free allows 2 PDFs/day up to 120 pages each. Plus is $5/mo for unlimited PDFs up to 2,000 pages.",
        pricing_source_url="https://www.chatpdf.com/pricing",
        last_verified_date="2026-03-01",
        android_available=False,
        known_limitations=[
            "Free tier capped at 2 PDFs per 24 hours.",
            "No native Android app (requires mobile web browser)."
        ],
        automation_capabilities=[
            AutomationCapability.WEB_BROWSER_LAUNCH,
            AutomationCapability.CLIPBOARD_QUERY_STAGING,
            AutomationCapability.MANUAL_GUIDANCE
        ],
        tags=["documents and pdfs", "pdf chat", "summary", "research papers", "contracts"]
    ),
    AppEntity(
        id="adobe_acrobat",
        name="Adobe Acrobat Reader",
        category="Documents and PDFs",
        official_url="https://www.adobe.com/acrobat/mobile.html",
        package_name="com.adobe.reader",
        description="Industry standard PDF viewer with annotation, signing, and AI assistant summarization.",
        best_use_case="Viewing, annotating, signing, and managing official documents on Android devices.",
        suitability_explanation="Most reliable offline PDF rendering and signature workflow on mobile.",
        pricing_model=PricingModel.FREEMIUM,
        pricing_details="Free for viewing and basic annotations. Acrobat Pro / AI Assistant add-on starts at $4.99/mo.",
        pricing_source_url="https://www.adobe.com/acrobat/pricing.html",
        last_verified_date="2026-03-01",
        android_available=True,
        known_limitations=[
            "AI Assistant features require separate paid add-on.",
            "App size is relatively heavy (>100MB)."
        ],
        automation_capabilities=[
            AutomationCapability.DEEP_LINK_LAUNCH,
            AutomationCapability.WEB_BROWSER_LAUNCH,
            AutomationCapability.MANUAL_GUIDANCE
        ],
        tags=["documents and pdfs", "pdf viewer", "annotations", "signing", "official"]
    ),

    # --- Writing ---
    AppEntity(
        id="grammarly",
        name="Grammarly",
        category="Writing",
        official_url="https://www.grammarly.com",
        package_name="com.grammarly.android.keyboard",
        description="AI writing assistant offering grammar checks, clarity suggestions, and tone adjustment.",
        best_use_case="Polishing emails, academic papers, and professional messages directly on mobile keyboard.",
        suitability_explanation="Integrates as an Android system keyboard to check text across all apps seamlessly.",
        pricing_model=PricingModel.FREEMIUM,
        pricing_details="Free tier includes spelling, grammar, and punctuation. Premium starts at $12/mo.",
        pricing_source_url="https://www.grammarly.com/plans",
        last_verified_date="2026-03-01",
        android_available=True,
        known_limitations=[
            "Advanced clarity and vocabulary enhancements require Premium.",
            "Requires keyboard input method permissions."
        ],
        automation_capabilities=[
            AutomationCapability.DEEP_LINK_LAUNCH,
            AutomationCapability.WEB_BROWSER_LAUNCH,
            AutomationCapability.MANUAL_GUIDANCE
        ],
        tags=["writing", "grammar", "keyboard", "editing", "tone", "proofreading"]
    ),
    AppEntity(
        id="quillbot",
        name="QuillBot",
        category="Writing",
        official_url="https://quillbot.com",
        package_name="com.quillbot.android",
        description="AI paraphrasing, summarization, and citation generation tool for writers and students.",
        best_use_case="Rewording sentences, avoiding redundancy, and generating APA/MLA citations.",
        suitability_explanation="Offers multiple writing modes (Standard, Fluency, Formal) with interactive thesaurus.",
        pricing_model=PricingModel.FREEMIUM,
        pricing_details="Free paraphrases up to 125 words at a time. Premium is $9.95/mo for unlimited words.",
        pricing_source_url="https://quillbot.com/upgrade",
        last_verified_date="2026-03-01",
        android_available=True,
        known_limitations=[
            "Word limit on free paraphrasing prompts.",
            "Advanced tone modes locked behind subscription."
        ],
        automation_capabilities=[
            AutomationCapability.DEEP_LINK_LAUNCH,
            AutomationCapability.WEB_BROWSER_LAUNCH,
            AutomationCapability.CLIPBOARD_QUERY_STAGING,
            AutomationCapability.MANUAL_GUIDANCE
        ],
        tags=["writing", "paraphrase", "citations", "academic writing", "summary"]
    ),

    # --- Image and Video Creation ---
    AppEntity(
        id="canva",
        name="Canva",
        category="Image and video creation",
        official_url="https://www.canva.com",
        package_name="com.canva.editor",
        description="Comprehensive graphic design, presentation, and video editing tool with AI Magic Studio.",
        best_use_case="Creating social media graphics, posters, presentations, and thumbnails quickly.",
        suitability_explanation="Massive template library combined with intuitive mobile touch controls and free export.",
        pricing_model=PricingModel.FREEMIUM,
        pricing_details="Free tier includes thousands of templates and cloud export. Canva Pro is $12.99/mo.",
        pricing_source_url="https://www.canva.com/pricing",
        last_verified_date="2026-03-01",
        android_available=True,
        known_limitations=[
            "Premium photos, background remover, and Magic Switch require Pro.",
            "Requires stable internet connection for template assets."
        ],
        automation_capabilities=[
            AutomationCapability.DEEP_LINK_LAUNCH,
            AutomationCapability.WEB_BROWSER_LAUNCH,
            AutomationCapability.MANUAL_GUIDANCE
        ],
        tags=["image and video creation", "graphic design", "presentations", "templates", "social media"]
    ),
    AppEntity(
        id="capcut",
        name="CapCut",
        category="Image and video creation",
        official_url="https://www.capcut.com",
        package_name="com.lemon.lvoverseas",
        description="All-in-one video editing app featuring auto-captions, trendy transitions, and AI video filters.",
        best_use_case="Editing short-form TikTok, Reels, and YouTube Shorts videos directly on phone.",
        suitability_explanation="Exceptional mobile video editing speed with accurate speech-to-caption generation.",
        pricing_model=PricingModel.FREEMIUM,
        pricing_details="Free tier includes timeline editing and standard exports. CapCut Pro is $9.99/mo.",
        pricing_source_url="https://www.capcut.com/pricing",
        last_verified_date="2026-03-01",
        android_available=True,
        known_limitations=[
            "Certain viral filters and 4K cloud exports require Pro.",
            "Availability varies by regional app stores."
        ],
        automation_capabilities=[
            AutomationCapability.DEEP_LINK_LAUNCH,
            AutomationCapability.WEB_BROWSER_LAUNCH,
            AutomationCapability.MANUAL_GUIDANCE
        ],
        tags=["image and video creation", "video editing", "reels", "shorts", "captions", "filters"]
    ),

    # --- Productivity ---
    AppEntity(
        id="notion",
        name="Notion",
        category="Productivity",
        official_url="https://www.notion.so",
        package_name="notion.id",
        description="Connected workspace for notes, docs, databases, project management, and AI synthesis.",
        best_use_case="Organizing research notes, project roadmaps, study wikis, and knowledge bases.",
        suitability_explanation="Hyper-flexible relational databases and markdown notes sync seamlessly between Android and web.",
        pricing_model=PricingModel.FREEMIUM,
        pricing_details="Free for individuals with unlimited pages. Plus is $10/mo; Notion AI is $10/mo add-on.",
        pricing_source_url="https://www.notion.so/pricing",
        last_verified_date="2026-03-01",
        android_available=True,
        known_limitations=[
            "Complex nested databases can be slow to scroll on older Android devices.",
            "Offline mode is limited to recently opened pages."
        ],
        automation_capabilities=[
            AutomationCapability.DEEP_LINK_LAUNCH,
            AutomationCapability.WEB_BROWSER_LAUNCH,
            AutomationCapability.CLIPBOARD_QUERY_STAGING,
            AutomationCapability.MANUAL_GUIDANCE
        ],
        tags=["productivity", "notes", "knowledge base", "databases", "collaboration", "wiki"]
    ),
    AppEntity(
        id="todoist",
        name="Todoist",
        category="Productivity",
        official_url="https://todoist.com",
        package_name="com.todoist",
        description="Task manager and to-do list with natural language date parsing and productivity karma.",
        best_use_case="Daily task organization, habit tracking, and project deadline management.",
        suitability_explanation="Best natural language parsing: typing 'Buy books every Friday at 5pm' schedules automatically.",
        pricing_model=PricingModel.FREEMIUM,
        pricing_details="Free allows up to 5 active projects. Pro is $4/mo for 300 projects and reminders.",
        pricing_source_url="https://todoist.com/pricing",
        last_verified_date="2026-03-01",
        android_available=True,
        known_limitations=[
            "Task reminders require paid Pro tier.",
            "Activity log history on free plan is limited to 1 week."
        ],
        automation_capabilities=[
            AutomationCapability.DEEP_LINK_LAUNCH,
            AutomationCapability.WEB_BROWSER_LAUNCH,
            AutomationCapability.MANUAL_GUIDANCE
        ],
        tags=["productivity", "task manager", "todo", "habits", "reminders"]
    )
]

def get_catalogue() -> List[AppEntity]:
    return CURATED_CATALOGUE

def get_app_by_id(app_id: str) -> Optional[AppEntity]:
    for app in CURATED_CATALOGUE:
        if app.id.lower() == app_id.lower():
            return app
    return None
