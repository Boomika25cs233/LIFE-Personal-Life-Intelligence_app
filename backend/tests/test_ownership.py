from app.services.ownership import (
    collect_ownership_evidence,
    is_sender_learned
)

from app.models.sender_model import SenderDB


def test_evidence_includes_sender_confirmed(db_session):
    sender = SenderDB(
        name="Electricity Board",
        is_confirmed=True,
        confirmation_count=1
    )

    db_session.add(sender)
    db_session.commit()

    flags = collect_ownership_evidence(
        db_session,
        "ELECTRICITY_BILL",
        {"sender": "Electricity Board"}
    )

    assert "SENDER_PREVIOUSLY_CONFIRMED" in flags
    assert "KNOWN_EVENT_CATEGORY" in flags


def test_evidence_empty_for_unconfirmed_sender(db_session):
    flags = collect_ownership_evidence(
        db_session,
        "ELECTRICITY_BILL",
        {"sender": "Some New Sender"}
    )

    assert "SENDER_PREVIOUSLY_CONFIRMED" not in flags
    assert "KNOWN_EVENT_CATEGORY" in flags


def test_evidence_empty_for_unknown_event(db_session):
    flags = collect_ownership_evidence(
        db_session,
        "UNKNOWN_EVENT",
        {"sender": None}
    )

    assert flags == []


def test_sender_learned_after_threshold(db_session):
    sender = SenderDB(
        name="Electricity Board",
        is_confirmed=True,
        confirmation_count=3
    )

    db_session.add(sender)
    db_session.commit()

    assert is_sender_learned(
        db_session,
        "Electricity Board"
    ) is True


def test_sender_not_learned_below_threshold(db_session):
    sender = SenderDB(
        name="Electricity Board",
        is_confirmed=True,
        confirmation_count=1
    )

    db_session.add(sender)
    db_session.commit()

    assert is_sender_learned(
        db_session,
        "Electricity Board"
    ) is False