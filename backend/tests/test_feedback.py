from app.services.feedback import record_feedback
from app.models.event_model import EventDB
from app.models.sender_model import SenderDB


def _create_test_event(db_session):
    event = EventDB(
        event_id="EVT-001",
        event_type="ELECTRICITY_BILL",
        category="FINANCE",
        responsibility="BILL_PAYMENT",
        sender="Electricity Board",
        ownership_state="LIKELY",
        confidence=0.65,
        fingerprint="fp-001",
    )

    db_session.add(event)
    db_session.commit()

    return event


def test_confirm_creates_sender_and_increments_count(db_session):
    _create_test_event(db_session)

    result = record_feedback(
        db_session,
        "EVT-001",
        "confirm"
    )

    assert result["success"] is True

    sender = (
        db_session.query(SenderDB)
        .filter(SenderDB.name == "Electricity Board")
        .first()
    )

    assert sender is not None
    assert sender.is_confirmed is True
    assert sender.confirmation_count == 1


def test_confirm_twice_increments_count_to_two(db_session):
    _create_test_event(db_session)

    record_feedback(
        db_session,
        "EVT-001",
        "CONFIRM"
    )

    record_feedback(
        db_session,
        "EVT-001",
        "CONFIRM"
    )

    sender = (
        db_session.query(SenderDB)
        .filter(SenderDB.name == "Electricity Board")
        .first()
    )

    assert sender.confirmation_count == 2


def test_reject_resets_ownership_and_confidence(db_session):
    event = _create_test_event(db_session)

    record_feedback(
        db_session,
        "EVT-001",
        "REJECT"
    )

    db_session.refresh(event)

    assert event.ownership_state == "UNKNOWN"
    assert event.confidence == 0.0


def test_invalid_feedback_type_rejected(db_session):
    _create_test_event(db_session)

    result = record_feedback(
        db_session,
        "EVT-001",
        "MAYBE"
    )

    assert result["success"] is False
    assert "Invalid feedback_type" in result["error"]


def test_feedback_for_nonexistent_event(db_session):
    result = record_feedback(
        db_session,
        "EVT-DOES-NOT-EXIST",
        "CONFIRM"
    )

    assert result["success"] is False
    assert "not found" in result["error"]


def test_ignore_and_complete_are_valid(db_session):
    _create_test_event(db_session)

    result1 = record_feedback(
        db_session,
        "EVT-001",
        "IGNORE"
    )

    result2 = record_feedback(
        db_session,
        "EVT-001",
        "COMPLETE"
    )

    assert result1["success"] is True
    assert result2["success"] is True