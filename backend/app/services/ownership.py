from typing import Dict, Any, List

from sqlalchemy.orm import Session

from app.models.sender_model import SenderDB


LEARNED_CONFIRMATION_THRESHOLD = 3


def collect_ownership_evidence(
    db: Session,
    event_type: str,
    entities: Dict[str, Any]
) -> List[str]:
    """
    Collects ownership evidence by querying real database history.
    """

    sender = entities.get("sender")
    evidence_flags: List[str] = []

    sender_row = None

    if sender is not None:
        sender_row = (
            db.query(SenderDB)
            .filter(SenderDB.name == sender)
            .first()
        )

    if sender_row is not None and sender_row.is_confirmed:
        evidence_flags.append(
            "SENDER_PREVIOUSLY_CONFIRMED"
        )

    if event_type != "UNKNOWN_EVENT":
        evidence_flags.append(
            "KNOWN_EVENT_CATEGORY"
        )

    return evidence_flags


def is_sender_learned(
    db: Session,
    sender: str
) -> bool:
    """
    A sender becomes LEARNED after enough confirmations.
    """

    if sender is None:
        return False

    sender_row = (
        db.query(SenderDB)
        .filter(SenderDB.name == sender)
        .first()
    )

    if sender_row is None:
        return False

    return (
        sender_row.confirmation_count
        >= LEARNED_CONFIRMATION_THRESHOLD
    )