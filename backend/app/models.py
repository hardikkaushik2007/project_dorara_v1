from enum import Enum
from typing import List, Optional, Dict, Any
from pydantic import BaseModel, Field

class PricingModel(str, Enum):
    FREE = "FREE"
    FREEMIUM = "FREEMIUM"
    PAID = "PAID"
    FREE_TRIAL = "FREE_TRIAL"
    UNKNOWN = "UNKNOWN"

class AutomationCapability(str, Enum):
    DEEP_LINK_LAUNCH = "DEEP_LINK_LAUNCH"
    WEB_BROWSER_LAUNCH = "WEB_BROWSER_LAUNCH"
    CLIPBOARD_QUERY_STAGING = "CLIPBOARD_QUERY_STAGING"
    ASSISTED_FORM_FILLING = "ASSISTED_FORM_FILLING"
    MANUAL_GUIDANCE = "MANUAL_GUIDANCE"

class ActionStatus(str, Enum):
    REQUESTED = "REQUESTED"
    ATTEMPTED = "ATTEMPTED"
    COMPLETED = "COMPLETED"
    VERIFIED = "VERIFIED"
    FAILED = "FAILED"

class AppEntity(BaseModel):
    id: str
    name: str
    category: str
    official_url: str
    package_name: Optional[str] = None
    description: str
    best_use_case: str
    suitability_explanation: str
    pricing_model: PricingModel
    pricing_details: str
    pricing_source_url: str
    last_verified_date: str
    android_available: bool
    known_limitations: List[str]
    automation_capabilities: List[AutomationCapability]
    tags: List[str]
    confidence_score: float = 1.0

class IntentAnalysisRequest(BaseModel):
    query: str
    language: Optional[str] = "en"

class IntentAnalysisResponse(BaseModel):
    original_query: str
    primary_intent: str
    target_category: str
    extracted_keywords: List[str]
    is_academic: bool = False
    is_coding: bool = False
    is_creative: bool = False
    is_healthcare: bool = False
    suggested_clarification: Optional[str] = None

class RecommendationRequest(BaseModel):
    query: str
    category_filter: Optional[str] = None
    max_results: int = 5
    only_free: bool = False
    only_android: bool = False

class RecommendationResponse(BaseModel):
    query: str
    top_recommendation_reason: str
    items: List[AppEntity]

class ComparisonRequest(BaseModel):
    app_ids: List[str]

class ComparisonCriterion(BaseModel):
    title: str
    app_values: Dict[str, str]

class ComparisonResponse(BaseModel):
    compared_apps: List[AppEntity]
    summary_recommendation: str
    criteria: List[ComparisonCriterion]

class TaskStep(BaseModel):
    step_id: str
    title: str
    description: str
    status: ActionStatus
    execution_method: str
    action_type: str
    observable_success_criteria: str
    actual_result: Optional[str] = None
    recovery_guidance: Optional[str] = None

class TaskSession(BaseModel):
    session_id: str
    original_request: str
    selected_app_id: Optional[str] = None
    status: ActionStatus
    current_step_index: int = 0
    staged_query: Optional[str] = None
    steps: List[TaskStep] = []
    created_at: str
