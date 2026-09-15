import hashlib
from typing import Dict, Any

from sqlalchemy.orm import Session

from app.models.event_model import EventDB


def build_fingerprint(
    event_type: str,
    entities: Dict[str, Any]
) -> str:
    """
    Builds a stable fingerprint from structured event fields.
    """

    amount = entities.get("amount")
    due_date = entities.get("due_date")
    sender = entities.get("sender")
    reference_number = entities.get("reference_number")

    raw_key = "|".join([
        event_type,
        str(amount) if amount is not None else "NONE",
        str(due_date) if due_date is not None else "NONE",
        str(sender) if sender is not None else "NONE",
        str(reference_number)
        if reference_number is not None
        else "NONE",
    ])

    return hashlib.sha256(
        raw_key.encode("utf-8")
    ).hexdigest()


def is_duplicate(
    db: Session,
    fingerprint: str
) -> bool:
    """
    Checks the real events table for an existing
    matching fingerprint.
    """

    existing = (
        db.query(EventDB)
        .filter(EventDB.fingerprint == fingerprint)
        .first()
    )

    return existing is not None