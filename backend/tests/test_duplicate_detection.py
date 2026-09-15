from app.services.duplicate_detection import (
    build_fingerprint,
    is_duplicate
)

from app.models.event_model import EventDB


def test_same_entities_same_fingerprint():
    entities = {
        "amount": 2850,
        "due_date": "2026-09-22",
        "sender": "Electricity Board",
        "reference_number": None
    }

    fp1 = build_fingerprint(
        "ELECTRICITY_BILL",
        entities
    )

    fp2 = build_fingerprint(
        "ELECTRICITY_BILL",
        entities
    )

    assert fp1 == fp2


def test_different_amount_different_fingerprint():
    entities1 = {
        "amount": 2850,
        "due_date": "2026-09-22",
        "sender": "Electricity Board",
        "reference_number": None
    }

    entities2 = {
        "amount": 3000,
        "due_date": "2026-09-22",
        "sender": "Electricity Board",
        "reference_number": None
    }

    fp1 = build_fingerprint(
        "ELECTRICITY_BILL",
        entities1
    )

    fp2 = build_fingerprint(
        "ELECTRICITY_BILL",
        entities2
    )

    assert fp1 != fp2


def test_different_reference_number_different_fingerprint():
    entities1 = {
        "amount": 500,
        "due_date": None,
        "sender": "Amazon",
        "reference_number": "ORD111"
    }

    entities2 = {
        "amount": 500,
        "due_date": None,
        "sender": "Amazon",
        "reference_number": "ORD222"
    }

    fp1 = build_fingerprint(
        "ORDER_SHIPPED",
        entities1
    )

    fp2 = build_fingerprint(
        "ORDER_SHIPPED",
        entities2
    )

    assert fp1 != fp2


def test_is_duplicate_true_when_fingerprint_exists(
    db_session
):
    event = EventDB(
        event_id="EVT-001",
        event_type="ELECTRICITY_BILL",
        fingerprint="abc123"
    )

    db_session.add(event)
    db_session.commit()

    assert is_duplicate(
        db_session,
        "abc123"
    ) is True


def test_is_duplicate_false_when_fingerprint_not_seen(
    db_session
):
    assert is_duplicate(
        db_session,
        "xyz789"
    ) is False


def test_is_duplicate_false_for_empty_database(
    db_session
):
    assert is_duplicate(
        db_session,
        "anything"
    ) is False


def test_missing_fields_still_produce_consistent_fingerprint():
    entities = {
        "amount": None,
        "due_date": None,
        "sender": None,
        "reference_number": None
    }

    fp1 = build_fingerprint(
        "UNKNOWN_EVENT",
        entities
    )

    fp2 = build_fingerprint(
        "UNKNOWN_EVENT",
        entities
    )

    assert fp1 == fp2