from fastapi import FastAPI, HTTPException, Query
from fastapi.middleware.cors import CORSMiddleware
from typing import List, Optional

from app.models import (
    AppEntity, IntentAnalysisRequest, IntentAnalysisResponse,
    RecommendationRequest, RecommendationResponse,
    ComparisonRequest, ComparisonResponse
)
from app.catalogue import get_catalogue, get_app_by_id
from app.engine import analyze_intent, recommend_apps, compare_apps

app = FastAPI(
    title="Project Dora V1 Backend Service",
    description="Intelligent App Discovery, Recommendation, and Intent Analysis Engine for Dora AI Companion",
    version="1.0.0"
)

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

@app.get("/")
def root():
    return {
        "message": "Project Dora V1 Backend is live and running!",
        "service": "project-dora-backend",
        "docs_url": "/docs",
        "health_check": "/api/v1/health",
        "total_apps": len(get_catalogue())
    }

@app.get("/api/v1/health")
def health_check():
    return {
        "status": "healthy",
        "service": "project-dora-backend",
        "version": "1.0.0",
        "total_catalogue_apps": len(get_catalogue())
    }

@app.get("/api/v1/categories", response_model=List[str])
def list_categories():
    catalogue = get_catalogue()
    categories = sorted(list(set(app.category for app in catalogue)))
    return categories

@app.get("/api/v1/apps", response_model=List[AppEntity])
def list_apps(
    category: Optional[str] = Query(None, description="Filter by category"),
    search: Optional[str] = Query(None, description="Search by name or keyword"),
    only_android: bool = Query(False, description="Filter only apps with Android availability"),
    only_free: bool = Query(False, description="Filter only free/freemium apps")
):
    apps = get_catalogue()
    if category:
        apps = [a for a in apps if a.category.lower() == category.lower()]
    if only_android:
        apps = [a for a in apps if a.android_available]
    if only_free:
        apps = [a for a in apps if a.pricing_model.value in ["FREE", "FREEMIUM"]]
    if search:
        s_lower = search.lower()
        apps = [
            a for a in apps 
            if s_lower in a.name.lower() or s_lower in a.description.lower() or any(s_lower in t.lower() for t in a.tags)
        ]
    return apps

@app.get("/api/v1/apps/{app_id}", response_model=AppEntity)
def get_app(app_id: str):
    found = get_app_by_id(app_id)
    if not found:
        raise HTTPException(status_code=404, detail=f"Application '{app_id}' not found in catalogue")
    return found

@app.post("/api/v1/intent", response_model=IntentAnalysisResponse)
def endpoint_intent(req: IntentAnalysisRequest):
    return analyze_intent(req.query, req.language or "en")

@app.post("/api/v1/recommend", response_model=RecommendationResponse)
def endpoint_recommend(req: RecommendationRequest):
    return recommend_apps(req)

@app.post("/api/v1/compare", response_model=ComparisonResponse)
def endpoint_compare(req: ComparisonRequest):
    return compare_apps(req)
