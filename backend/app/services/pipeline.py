from typing import Dict, Any

from sqlalchemy.orm import Session

from app.services.preprocessing import preprocess_text
from app.services.entity_extraction import extract_entities
from app.services.event_detection import detect_event_type
from app.core.taxonomy import get_category, get_responsibility
from app.services.ownership import (
    collect_ownership_evidence,
    is_sender_learned,
)
from app.services.confidence import calculate_confidence
from app.services.evidence import generate_evidence
from app.services.explainability import generate_explanation
from app.services.duplicate_detection import (
    build_fingerprint,
    is_duplicate,
)


def run_pipeline(
    db: Session,
    source: str,
    text: str
) -> Dict[str, Any]:
    """
    Runs the full detection pipeline WITHOUT saving to the database.

    Used by both the /detect endpoint and automated tests.
    """

    cleaned_text = preprocess_text(text)

    entities = extract_entities(cleaned_text)

    detection = detect_event_type(cleaned_text)
    event_type = detection["event_type"]

    category = get_category(event_type)
    responsibility = get_responsibility(event_type)

    fingerprint = build_fingerprint(
        event_type,
        entities
    )

    duplicate = is_duplicate(
        db,
        fingerprint
    )

    evidence_flags = collect_ownership_evidence(
        db,
        event_type,
        entities
    )

    confidence_result = calculate_confidence(
        evidence_flags
    )

    ownership_state = confidence_result["state"]

    sender = entities.get("sender")

    if (
        is_sender_learned(db, sender)
        and ownership_state in ("LIKELY", "CONFIRMED")
    ):
        ownership_state = "LEARNED"

    evidence_sentences = generate_evidence(
        confidence_result["contributing_factors"]
    )

    explanation_result = generate_explanation(
        ownership_state,
        evidence_sentences
    )

    return {
        "cleaned_text": cleaned_text,
        "entities": entities,
        "event_type": event_type,
        "category": category,
        "responsibility": responsibility,
        "ownership_state": ownership_state,
        "confidence": confidence_result["confidence"],
        "evidence": evidence_sentences,
        "explanation": explanation_result["explanation"],
        "fingerprint": fingerprint,
        "is_duplicate": duplicate,
    }