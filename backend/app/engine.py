import re
from typing import List, Dict, Tuple
from app.models import (
    AppEntity, IntentAnalysisResponse, RecommendationRequest, 
    RecommendationResponse, ComparisonRequest, ComparisonResponse, 
    ComparisonCriterion, PricingModel
)
from app.catalogue import get_catalogue, get_app_by_id

CATEGORY_KEYWORDS = {
    "AI research": ["ai", "research", "search", "answer", "perplexity", "chatgpt", "claude", "information", "synthesis"],
    "Academic research": ["academic", "paper", "papers", "study", "clinical", "healthcare", "medicine", "consensus", "elicit", "scholar", "literature", "citations"],
    "Studying and education": ["study", "exam", "education", "quizlet", "khan", "learn", "flashcards", "math", "revision"],
    "Coding": ["code", "coding", "developer", "programming", "python", "javascript", "website", "build a website", "github", "replit", "bug", "compiler"],
    "Writing": ["write", "writing", "essay", "grammar", "paraphrase", "email", "proofread", "grammarly", "quillbot"],
    "Image and video creation": ["image", "video", "photo", "edit video", "editing", "canva", "capcut", "graphic", "reels", "shorts", "thumbnail"],
    "Documents and PDFs": ["pdf", "document", "contract", "chatpdf", "acrobat", "read pdf", "summarize pdf", "explain a pdf"],
    "Productivity": ["productivity", "notes", "todo", "task", "organize", "habits", "notion", "todoist", "workflow"]
}

HINGLISH_PATTERNS = [
    r"\b(mujhe|karni|karna|chahiye|batao|kaise|hai|hain|karo|bata)\b"
]

def analyze_intent(query: str, language: str = "en") -> IntentAnalysisResponse:
    q_lower = query.lower()
    is_hinglish = any(re.search(pat, q_lower) for pat in HINGLISH_PATTERNS)
    
    is_healthcare = any(w in q_lower for w in ["healthcare", "health", "medicine", "clinical", "doctor", "medical", "disease"])
    is_academic = any(w in q_lower for w in ["academic", "paper", "papers", "literature", "journal", "citation", "peer-reviewed", "thesis"])
    is_coding = any(w in q_lower for w in ["code", "coding", "program", "developer", "website", "bug", "software", "app development"])
    is_creative = any(w in q_lower for w in ["video", "image", "photo", "edit", "graphic", "thumbnail", "reels", "design"])

    # Determine target category
    scores: Dict[str, int] = {cat: 0 for cat in CATEGORY_KEYWORDS}
    for cat, keywords in CATEGORY_KEYWORDS.items():
        for kw in keywords:
            if kw in q_lower:
                scores[cat] += 2

    # Boost academic if healthcare or papers mentioned
    if is_healthcare or is_academic:
        scores["Academic research"] += 4
        scores["AI research"] += 2

    if "pdf" in q_lower:
        scores["Documents and PDFs"] += 5

    best_cat = max(scores, key=scores.get)
    if scores[best_cat] == 0:
        best_cat = "AI research"

    # Clarification suggestion if query is very broad
    clarification = None
    if q_lower.strip() in ["research", "ai", "study", "help", "tools", "find tool"]:
        clarification = "Kya tum broad web exploration chahte ho ya peer-reviewed academic papers par focus karna hai?"

    extracted = [w for w in re.findall(r"\b\w{3,}\b", q_lower) if w not in ["the", "and", "for", "with", "tool", "apps", "help", "mujhe", "karni"]]

    return IntentAnalysisResponse(
        original_query=query,
        primary_intent=f"Discover digital tools for {best_cat.lower()}",
        target_category=best_cat,
        extracted_keywords=extracted,
        is_academic=is_academic,
        is_coding=is_coding,
        is_creative=is_creative,
        is_healthcare=is_healthcare,
        suggested_clarification=clarification
    )

def score_app(app: AppEntity, query: str, intent: IntentAnalysisResponse) -> float:
    score = 0.0
    q_lower = query.lower()
    
    # Exact name match
    if app.name.lower() in q_lower:
        score += 25.0

    # Category alignment
    if app.category.lower() == intent.target_category.lower():
        score += 15.0

    # Healthcare specialization
    if intent.is_healthcare:
        if "healthcare" in app.tags or "clinical" in app.tags or "medicine" in app.tags:
            score += 12.0
        if app.id in ["consensus", "elicit", "perplexity"]:
            score += 8.0

    # Academic specialization
    if intent.is_academic and app.category == "Academic research":
        score += 10.0

    # Tag and description overlap
    for tag in app.tags:
        if tag in q_lower:
            score += 5.0
            
    for kw in intent.extracted_keywords:
        if kw in app.description.lower():
            score += 3.0
        if kw in app.best_use_case.lower():
            score += 4.0

    return score

def recommend_apps(request: RecommendationRequest) -> RecommendationResponse:
    catalogue = get_catalogue()
    intent = analyze_intent(request.query)

    # Filter by user constraints
    candidates = catalogue
    if request.category_filter:
        candidates = [c for c in candidates if c.category.lower() == request.category_filter.lower()]
    if request.only_free:
        candidates = [c for c in candidates if c.pricing_model in [PricingModel.FREE, PricingModel.FREEMIUM]]
    if request.only_android:
        candidates = [c for c in candidates if c.android_available]

    # Score each candidate
    scored: List[Tuple[AppEntity, float]] = []
    for app in candidates:
        s = score_app(app, request.query, intent)
        scored.append((app, s))

    scored.sort(key=lambda x: x[1], reverse=True)
    top_items = [item[0] for item in scored[:request.max_results]]

    # Formulate honest recommendation rationale
    if not top_items:
        rationale = "No exact application matched all criteria in the current verified catalogue."
    else:
        top_app = top_items[0]
        if intent.is_healthcare and top_app.id in ["perplexity", "consensus", "elicit"]:
            rationale = (
                f"For healthcare and clinical research, {top_app.name} is recommended because it "
                f"provides direct scientific source citations. If you need peer-reviewed evidence synthesis, "
                f"Consensus or Elicit offer specialized clinical extraction."
            )
        elif intent.target_category == "Coding":
            rationale = (
                f"{top_app.name} suits this workflow best based on its developer-specific synthesis "
                f"and rapid code execution capabilities."
            )
        else:
            rationale = (
                f"{top_app.name} is ranked top for '{request.query}' based on verified feature fit: "
                f"{top_app.best_use_case}"
            )

    return RecommendationResponse(
        query=request.query,
        top_recommendation_reason=rationale,
        items=top_items
    )

def compare_apps(request: ComparisonRequest) -> ComparisonResponse:
    apps: List[AppEntity] = []
    for aid in request.app_ids:
        found = get_app_by_id(aid)
        if found:
            apps.append(found)

    if not apps:
        return ComparisonResponse(
            compared_apps=[],
            summary_recommendation="No valid applications selected for comparison.",
            criteria=[]
        )

    criteria = [
        ComparisonCriterion(
            title="Task Suitability",
            app_values={a.name: a.best_use_case for a in apps}
        ),
        ComparisonCriterion(
            title="Pricing Model",
            app_values={a.name: f"{a.pricing_model.value}: {a.pricing_details}" for a in apps}
        ),
        ComparisonCriterion(
            title="Free Tier Limitations",
            app_values={a.name: "; ".join(a.known_limitations) for a in apps}
        ),
        ComparisonCriterion(
            title="Android Availability",
            app_values={a.name: "Yes (Native App)" if a.android_available else "Web Only (Mobile Browser)" for a in apps}
        ),
        ComparisonCriterion(
            title="Automation Capabilities",
            app_values={a.name: ", ".join([c.value.replace("_", " ").title() for c in a.automation_capabilities]) for a in apps}
        ),
        ComparisonCriterion(
            title="Official Pricing Source",
            app_values={a.name: a.pricing_source_url for a in apps}
        ),
        ComparisonCriterion(
            title="Last Verification Date",
            app_values={a.name: a.last_verified_date for a in apps}
        )
    ]

    # Synthesis summary
    app_names = [a.name for a in apps]
    summary = f"Comparing {', '.join(app_names)}: "
    if len(apps) >= 2:
        summary += (
            f"{apps[0].name} excels in {apps[0].best_use_case}, whereas "
            f"{apps[1].name} is preferred for {apps[1].best_use_case}. "
            "Choose according to whether you require native Android app execution or deep academic paper synthesis."
        )
    else:
        summary += f"{apps[0].name} is verified for {apps[0].best_use_case}."

    return ComparisonResponse(
        compared_apps=apps,
        summary_recommendation=summary,
        criteria=criteria
    )
