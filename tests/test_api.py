import sys
import os

sys.path.insert(
    0,
    os.path.join(
        os.path.dirname(__file__),
        "..",
        "personal_intelligence",
        "api"
    )
)

from fastapi.testclient import TestClient
from main import app


client = TestClient(app)


def test_health_endpoint():
    response = client.get("/health")

    assert response.status_code == 200
    assert response.json()["status"] == "ok"


def test_analyze_event_endpoint():
    payload = {
        "event_type": "electricity_bill",
        "amount": 2850,
        "date": "2026-09-01",
        "category": "finance",
        "responsibility": "bill_payment",
        "ownership_confidence": 0.92,
        "state": "DUE",
        "due_date": "2026-09-05",
        "importance": 0.8,
        "urgency": 0.7
    }

    response = client.post(
        "/analyze-event",
        json=payload
    )

    assert response.status_code == 200

    data = response.json()

    assert "insight" in data
    assert data["anomaly"] is True


def test_analyze_event_unknown_type():
    payload = {
        "event_type": "made_up_thing",
        "amount": 100,
        "date": "2026-09-01",
        "category": "finance",
        "responsibility": "bill_payment",
        "ownership_confidence": 0.9
    }

    response = client.post(
        "/analyze-event",
        json=payload
    )

    assert response.status_code == 200
    assert "error" in response.json()


def test_analyze_event_invalid_amount_type():
    payload = {
        "event_type": "electricity_bill",
        "amount": "not-a-number",
        "date": "2026-09-01",
        "category": "finance",
        "responsibility": "bill_payment",
        "ownership_confidence": 0.9
    }

    response = client.post(
        "/analyze-event",
        json=payload
    )

    assert response.status_code == 422


def test_feedback_endpoint():
    payload = {
        "responsibility": "gas_refill",
        "feedback_type": "CONFIRM",
        "date": "2026-09-01"
    }

    response = client.post(
        "/feedback",
        json=payload
    )

    assert response.status_code == 200
    assert response.json()["status"] == "recorded"