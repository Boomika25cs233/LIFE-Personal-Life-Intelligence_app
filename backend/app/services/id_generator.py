from sqlalchemy.orm import Session
from sqlalchemy import func

from app.models.event_model import EventDB


def generate_event_id(db: Session) -> str:
    """
    Generates the next sequential event ID.

    Examples:
    EVT-001
    EVT-002
    EVT-003
    """

    count = db.query(
        func.count(EventDB.id)
    ).scalar()

    next_number = count + 1

    return f"EVT-{next_number:03d}"