from sqlalchemy import inspect

from app.database.db import engine
from app.models.event_model import EventDB
from app.models.sender_model import SenderDB


def test_events_table_exists():
    inspector = inspect(engine)
    tables = inspector.get_table_names()

    assert "events" in tables


def test_senders_table_exists():
    inspector = inspect(engine)
    tables = inspector.get_table_names()

    assert "senders" in tables


def test_events_table_has_expected_columns():
    inspector = inspect(engine)

    columns = [
        col["name"]
        for col in inspector.get_columns("events")
    ]

    for expected in [
        "event_id",
        "event_type",
        "category",
        "confidence",
        "fingerprint",
    ]:
        assert expected in columns