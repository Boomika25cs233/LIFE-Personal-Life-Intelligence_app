from sqlalchemy.orm import Session

from app.models.feedback_model import FeedbackDB
from app.models.sender_model import SenderDB
from app.models.event_model import EventDB


VALID_FEEDBACK_TYPES = [
    "CONFIRM",
    "REJECT",
    "IGNORE",
    "COMPLETE",
]


def record_feedback(
    db: Session,
    event_id: str,
    feedback_type: str
) -> dict:
    """
    Records user feedback for an event and updates
    related records when necessary.
    """

    feedback_type = feedback_type.upper()

    if feedback_type not in VALID_FEEDBACK_TYPES:
        return {
            "success": False,
            "error": f"Invalid feedback_type: {feedback_type}"
        }

    # 1. READ: check whether the event exists
    event = (
        db.query(EventDB)
        .filter(EventDB.event_id == event_id)
        .first()
    )

    if event is None:
        return {
            "success": False,
            "error": f"Event not found: {event_id}"
        }

    # 2. CREATE: save the feedback row
    feedback_row = FeedbackDB(
        event_id=event_id,
        feedback_type=feedback_type
    )

    db.add(feedback_row)

    # 3. UPDATE: CONFIRM strengthens sender trust
    if feedback_type == "CONFIRM" and event.sender:

        sender_row = (
            db.query(SenderDB)
            .filter(SenderDB.name == event.sender)
            .first()
        )

        if sender_row is None:
            # First confirmation for this sender
            sender_row = SenderDB(
                name=event.sender,
                is_confirmed=True,
                confirmation_count=1
            )

            db.add(sender_row)

        else:
            # Sender already exists
            sender_row.is_confirmed = True
            sender_row.confirmation_count += 1

    # 4. UPDATE: REJECT resets ownership confidence
    if feedback_type == "REJECT":
        event.ownership_state = "UNKNOWN"
        event.confidence = 0.0

    # Save everything to life.db
    db.commit()

    return {
        "success": True,
        "event_id": event_id,
        "feedback_type": feedback_type
    }