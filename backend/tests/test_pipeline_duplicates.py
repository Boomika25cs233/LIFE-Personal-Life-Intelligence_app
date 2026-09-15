from app.services.pipeline import run_pipeline
from app.models.event_model import EventDB


def test_resending_same_message_is_flagged_duplicate(db_session):
    text = "your electricity board bill of 2850 is due tomorrow"

    # First message should not be a duplicate
    first_result = run_pipeline(
        db_session,
        "SMS",
        text
    )

    assert first_result["is_duplicate"] is False

    # Simulate saving the first event,
    # just like the real /detect endpoint does.
    event_row = EventDB(
        event_id="EVT-001",
        event_type=first_result["event_type"],
        category=first_result["category"],
        responsibility=first_result["responsibility"],
        ownership_state=first_result["ownership_state"],
        confidence=first_result["confidence"],
        fingerprint=first_result["fingerprint"],
    )

    db_session.add(event_row)
    db_session.commit()

    # Send the exact same message again
    second_result = run_pipeline(
        db_session,
        "SMS",
        text
    )

    assert second_result["is_duplicate"] is True