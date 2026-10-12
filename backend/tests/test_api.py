import pytest
from fastapi.testclient import TestClient
from app.main import app
from app.models import PricingModel

client = TestClient(app)

def test_health():
    response = client.get("/api/v1/health")
    assert response.status_code == 200
    data = response.json()
    assert data["status"] == "healthy"
    assert data["total_catalogue_apps"] >= 15

def test_list_categories():
    response = client.get("/api/v1/categories")
    assert response.status_code == 200
    cats = response.json()
    assert "AI research" in cats
    assert "Coding" in cats
    assert "Academic research" in cats

def test_list_apps_filter():
    response = client.get("/api/v1/apps?category=AI research")
    assert response.status_code == 200
    items = response.json()
    assert len(items) > 0
    for it in items:
        assert it["category"] == "AI research"

def test_intent_analysis_hinglish():
    response = client.post("/api/v1/intent", json={"query": "Dora, mujhe AI ki help se research karni hai"})
    assert response.status_code == 200
    data = response.json()
    assert data["target_category"] in ["AI research", "Academic research"]

def test_recommendation_healthcare():
    response = client.post("/api/v1/recommend", json={
        "query": "Find me an AI tool for researching AI in healthcare.",
        "max_results": 4
    })
    assert response.status_code == 200
    data = response.json()
    app_ids = [a["id"] for a in data["items"]]
    # Should include top research tools like perplexity, consensus, elicit
    assert any(x in app_ids for x in ["perplexity", "consensus", "elicit"])
    assert "healthcare" in data["top_recommendation_reason"].lower()

def test_comparison():
    response = client.post("/api/v1/compare", json={"app_ids": ["perplexity", "consensus"]})
    assert response.status_code == 200
    data = response.json()
    assert len(data["compared_apps"]) == 2
    assert len(data["criteria"]) >= 5
    assert "Perplexity AI" in data["summary_recommendation"]
