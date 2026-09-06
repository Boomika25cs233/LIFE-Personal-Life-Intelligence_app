"""
Josi FastAPI Service
Exposes Josi's Personal Intelligence module over HTTP.
"""

import os
import sys
from datetime import date, datetime
from typing import Optional

from fastapi import FastAPI
from pydantic import BaseModel

BASE = os.path.dirname(__file__)
sys.path.append(os.path.join(BASE, ".."))
sys.path.append(os.path.join(BASE, "..", "models"))
sys.path.append(os.path.join(BASE, "..", "learning"))

from josi_engine import JosiEngine  # noqa: E402
from models import Event, Responsibility, Feedback  # noqa: E402
from learning_engine import record_feedback  # noqa: E402


app = FastAPI(title="Josi Personal Intelligence API")

# Map event_type -> which sample data file holds its history.
# In a real system this would come from the database (Phase 16) instead.
EVENT_TYPE_TO_FILE = {
    "electricity_bill": "electricity.json",
    "gas_refill": "gas.json",
    "fuel": "fuel.json",
    "medicine": "medicine.json",
    "subscription_netflix": "subscriptions.json",
    "rent": "rent.json",
    "emi_bike": "emi.json",
    "groceries": "groceries.json",
}


# ---------- Request/Response schemas ----------

class EventRequest(BaseModel):
    event_type: str
    amount: Optional[float] = None
    date: str                       # "YYYY-MM-DD"
    category: str
    responsibility: str
    ownership_confidence: float
    # Optional Dharshini context, sent alongside the event
    state: Optional[str] = None
    due_date: Optional[str] = None
    importance: Optional[float] = 0.5
    urgency: Optional[float] = 0.5


class FeedbackRequest(BaseModel):
    responsibility: str
    feedback_type: str              # CONFIRM / NOT_RELEVANT / IGNORE / COMPLETE
    date: str                       # "YYYY-MM-DD"


# ---------- Endpoints ----------

@app.get("/health")
def health():
    return {"status": "ok", "module": "josi-personal-intelligence"}


@app.post("/analyze-event")
def analyze_event(req: EventRequest):
    event = Event(
        event_type=req.event_type,
        amount=req.amount,
        date=datetime.strptime(req.date, "%Y-%m-%d").date(),
        category=req.category,
        responsibility=req.responsibility,
        ownership_confidence=req.ownership_confidence
    )

    responsibility_state = Responsibility(
        responsibility=req.event_type,
        state=req.state or "UNKNOWN",
        due_date=datetime.strptime(req.due_date, "%Y-%m-%d").date() if req.due_date else None,
        importance=req.importance or 0.5,
        urgency=req.urgency or 0.5
    )

    filename = EVENT_TYPE_TO_FILE.get(req.event_type)
    if not filename:
        return {"error": f"Unknown event_type '{req.event_type}'. No sample data mapped."}

    engine = JosiEngine(sample_data_filename=filename)
    result = engine.process_event(event, responsibility_state, today=date.today())
    return result


@app.post("/feedback")
def submit_feedback(req: FeedbackRequest):
    feedback = Feedback(
        responsibility=req.responsibility,
        feedback_type=req.feedback_type,
        date=datetime.strptime(req.date, "%Y-%m-%d").date()
    )
    updated_state = record_feedback(feedback)
    return {"status": "recorded", "learning_state": updated_state}


@app.get("/patterns")
def get_patterns(responsibility: str, sample_file: str):
    """
    Query pattern for a given responsibility.
    sample_file example: 'gas.json'
    """
    sys.path.append(os.path.join(BASE, "..", "data"))
    sys.path.append(os.path.join(BASE, "..", "patterns"))
    from historical_processor import load_historical_data
    from pattern_detector import detect_pattern

    history = load_historical_data(sample_file, responsibility_filter=responsibility)
    pattern = detect_pattern(responsibility, history)
    return {
        "responsibility": pattern.responsibility,
        "average_interval_days": pattern.average_interval_days,
        "is_recurring": pattern.is_recurring,
        "confidence": pattern.confidence,
        "intervals": pattern.intervals
    }


@app.get("/insights")
def get_insights(responsibility: str, sample_file: str):
    """
    Query a generated insight message for a given responsibility.
    """
    sys.path.append(os.path.join(BASE, "..", "data"))
    sys.path.append(os.path.join(BASE, "..", "patterns"))
    sys.path.append(os.path.join(BASE, "..", "baseline"))
    sys.path.append(os.path.join(BASE, "..", "prediction"))
    sys.path.append(os.path.join(BASE, "..", "insights"))
    from historical_processor import load_historical_data
    from pattern_detector import detect_pattern
    from baseline_engine import calculate_baseline
    from prediction_engine import predict_next_occurrence
    from insights_engine import generate_insight

    history = load_historical_data(sample_file, responsibility_filter=responsibility)
    pattern = detect_pattern(responsibility, history)
    baseline = calculate_baseline(responsibility, history)
    prediction = predict_next_occurrence(pattern, history)
    insight = generate_insight(responsibility, pattern=pattern, baseline=baseline, prediction=prediction)

    return {"responsibility": responsibility, "insight": insight.message}