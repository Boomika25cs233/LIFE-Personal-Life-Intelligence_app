from datetime import date
from models import HistoricalActivity
from pattern_detector import detect_pattern


def test_recurring_pattern_high_confidence():
    records = [
        HistoricalActivity("gas_refill", date(2026, 7, 1), 850),
        HistoricalActivity("gas_refill", date(2026, 7, 30), 850),
        HistoricalActivity("gas_refill", date(2026, 8, 28), 900),
    ]

    pattern = detect_pattern("gas_refill", records)

    assert pattern.is_recurring is True
    assert pattern.confidence == "HIGH"
    assert pattern.average_interval_days == 29.0


def test_irregular_pattern_low_confidence():
    records = [
        HistoricalActivity("gas_refill", date(2026, 7, 1), 850),
        HistoricalActivity("gas_refill", date(2026, 7, 20), 850),
        HistoricalActivity("gas_refill", date(2026, 8, 28), 900),
    ]

    pattern = detect_pattern("gas_refill", records)

    assert pattern.is_recurring is False
    assert pattern.confidence == "LOW"


def test_not_enough_data():
    records = [
        HistoricalActivity("gas_refill", date(2026, 7, 1), 850)
    ]

    pattern = detect_pattern("gas_refill", records)

    assert pattern.is_recurring is False
    assert pattern.confidence == "LOW"