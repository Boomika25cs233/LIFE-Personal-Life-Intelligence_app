from datetime import date
from models import HistoricalActivity
from baseline_engine import calculate_baseline
from anomaly_detector import detect_anomaly


def make_electricity_records():
    return [
        HistoricalActivity("electricity_bill", date(2026, 5, 1), 1200),
        HistoricalActivity("electricity_bill", date(2026, 6, 1), 1300),
        HistoricalActivity("electricity_bill", date(2026, 7, 1), 1350),
        HistoricalActivity("electricity_bill", date(2026, 8, 1), 1250),
    ]


def test_baseline_calculation():
    baseline = calculate_baseline(
        "electricity_bill",
        make_electricity_records()
    )

    assert baseline.average == 1275.0
    assert baseline.minimum == 1200.0
    assert baseline.maximum == 1350.0


def test_normal_bill_not_anomaly():
    baseline = calculate_baseline(
        "electricity_bill",
        make_electricity_records()
    )

    anomaly = detect_anomaly("electricity_bill", 1280, baseline)

    assert anomaly.is_anomaly is False


def test_abnormal_bill_is_anomaly():
    baseline = calculate_baseline(
        "electricity_bill",
        make_electricity_records()
    )

    anomaly = detect_anomaly("electricity_bill", 2850, baseline)

    assert anomaly.is_anomaly is True
    assert anomaly.anomaly_score > 0.5


def test_baseline_with_no_data():
    baseline = calculate_baseline("unknown", [])

    assert baseline.average == 0
    assert baseline.std_dev == 0