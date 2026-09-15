from typing import List, Optional

from fastapi import FastAPI, Depends, HTTPException
from sqlalchemy.orm import Session

from app.database.db import engine, Base, get_db
from app.models.event_model import EventDB
from app.models.sender_model import SenderDB
from app.models.feedback_model import FeedbackDB

from app.schemas.event_schema import EventDetectRequest, EventDetectResponse
from app.schemas.feedback_schema import FeedbackRequest, FeedbackResponse
from app.schemas.event_response_schema import EventRecord

from app.services.pipeline import run_pipeline
from app.services.feedback import record_feedback
from app.services.id_generator import generate_event_id


Base.metadata.create_all(bind=engine)


app = FastAPI(
    title="LIFE Event Intelligence API",
    description="Event detection and intelligence backend for LIFE",
    version="1.0.0",
)


@app.get("/")
def root():
    return {
        "message": "LIFE Event Intelligence API is running"
    }


@app.get("/health")
def health():
    return {
        "status": "healthy"
    }


@app.post(
    "/api/v1/events/detect",
    response_model=EventDetectResponse
)
def detect_event(
    request: EventDetectRequest,
    db: Session = Depends(get_db)
):
    result = run_pipeline(
        db,
        request.source,
        request.text
    )

    event_id = None

    if not result["is_duplicate"]:
        event_id = generate_event_id(db)

        event_row = EventDB(
            event_id=event_id,
            source=request.source,
            raw_text=request.text,
            cleaned_text=result["cleaned_text"],
            event_type=result["event_type"],
            category=result["category"],
            responsibility=result["responsibility"],
            amount=result["entities"].get("amount"),
            currency=result["entities"].get("currency"),
            due_date=result["entities"].get("due_date"),
            sender=result["entities"].get("sender"),
            reference_number=result["entities"].get(
                "reference_number"
            ),
            ownership_state=result["ownership_state"],
            confidence=result["confidence"],
            fingerprint=result["fingerprint"],
        )

        db.add(event_row)
        db.commit()

    return EventDetectResponse(
        event_id=event_id,
        event_type=result["event_type"],
        category=result["category"],
        responsibility=result["responsibility"],
        ownership=result["ownership_state"],
        confidence=result["confidence"],
        entities=result["entities"],
        evidence=result["evidence"],
        explanation=result["explanation"],
        is_duplicate=result["is_duplicate"],
        source=request.source,
    )


@app.post(
    "/api/v1/events/feedback",
    response_model=FeedbackResponse
)
def submit_feedback(
    request: FeedbackRequest,
    db: Session = Depends(get_db)
):
    result = record_feedback(
        db,
        request.event_id,
        request.feedback_type
    )

    return FeedbackResponse(
        success=result.get("success", False),
        event_id=result.get("event_id", ""),
        feedback_type=result.get("feedback_type", ""),
        error=result.get("error", ""),
    )


@app.get(
    "/api/v1/events/{event_id}",
    response_model=EventRecord
)
def get_event(
    event_id: str,
    db: Session = Depends(get_db)
):
    event = (
        db.query(EventDB)
        .filter(EventDB.event_id == event_id)
        .first()
    )

    if event is None:
        raise HTTPException(
            status_code=404,
            detail=f"Event not found: {event_id}"
        )

    return event


@app.get(
    "/api/v1/events",
    response_model=List[EventRecord]
)
def list_events(
    category: Optional[str] = None,
    event_type: Optional[str] = None,
    ownership: Optional[str] = None,
    limit: int = 50,
    db: Session = Depends(get_db),
):
    query = db.query(EventDB)

    if category:
        query = query.filter(
            EventDB.category == category
        )

    if event_type:
        query = query.filter(
            EventDB.event_type == event_type
        )

    if ownership:
        query = query.filter(
            EventDB.ownership_state == ownership
        )

    events = (
        query
        .order_by(EventDB.id.desc())
        .limit(limit)
        .all()
    )

    return events