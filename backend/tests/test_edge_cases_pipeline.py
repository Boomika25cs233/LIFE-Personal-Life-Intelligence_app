from datetime import datetime

from app.services.pipeline import run_pipeline
from app.services.entity_extraction import extract_date


def test_very_long_text_does_not_crash(db_session):
    long_text = "electricity bill due " * 500

    result = run_pipeline(
        db_session,
        "SMS",
        long_text
    )

    assert result["event_type"] == "ELECTRICITY_BILL"


def test_text_with_only_emoji(db_session):
    result = run_pipeline(
        db_session,
        "SMS",
        "😀🎉🔥💰"
    )

    assert result["event_type"] == "UNKNOWN_EVENT"


def test_text_with_mixed_languages(db_session):
    result = run_pipeline(
        db_session,
        "SMS",
        "आपका बिल ₹2850 बकाया है electricity bill due tomorrow"
    )

    assert result["event_type"] == "ELECTRICITY_BILL"


def test_text_with_multiple_currencies(db_session):
    result = run_pipeline(
        db_session,
        "SMS",
        "Bill of $50 or ₹2850, your electricity bill is due"
    )

    assert result["entities"]["currency"] in ("USD", "INR")


def test_sql_injection_style_text_is_handled_safely(db_session):
    malicious_text = "'; DROP TABLE events; -- electricity bill due"

    result = run_pipeline(
        db_session,
        "SMS",
        malicious_text
    )

    assert result["event_type"] == "ELECTRICITY_BILL"


def test_extremely_large_amount(db_session):
    result = run_pipeline(
        db_session,
        "SMS",
        "your electricity bill of ₹99999999999 is due"
    )

    assert result["entities"]["amount"] == 99999999999.0


def test_negative_looking_amount_text(db_session):
    result = run_pipeline(
        db_session,
        "SMS",
        "your balance adjustment of -2850 for electricity bill"
    )

    assert result["event_type"] == "ELECTRICITY_BILL"

    assert (
        result["entities"]["amount"] is None
        or result["entities"]["amount"] == 2850.0
    )


def test_null_byte_and_control_characters(db_session):
    weird_text = "electricity bill due\x00\x01\x02 tomorrow"

    result = run_pipeline(
        db_session,
        "SMS",
        weird_text
    )

    assert result["event_type"] == "ELECTRICITY_BILL"


def test_whitespace_only_text(db_session):
    result = run_pipeline(
        db_session,
        "SMS",
        "     \t\n   "
    )

    assert result["event_type"] == "UNKNOWN_EVENT"


def test_none_source_still_processes_text(db_session):
    result = run_pipeline(
        db_session,
        "MANUAL",
        "electricity bill due tomorrow"
    )

    assert result["event_type"] == "ELECTRICITY_BILL"


def test_date_next_monday():
    ref = datetime(2026, 9, 6)  # Sunday

    result = extract_date(
        "payment due next monday",
        reference_date=ref
    )

    assert result == "2026-09-07"


def test_date_next_weekday_same_day_means_next_week():
    ref = datetime(2026, 9, 7)  # Monday

    result = extract_date(
        "due next monday",
        reference_date=ref
    )

    assert result == "2026-09-14"