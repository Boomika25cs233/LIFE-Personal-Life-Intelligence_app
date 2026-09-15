from datetime import date
from models import HistoricalActivity, Pattern, Prediction, Responsibility
from prediction_engine import predict_next_occurrence
from context_engine import build_context


def test_prediction_from_recurring_pattern():
    records = [
        HistoricalActivity("gas_refill", date(2026, 7, 1), 850),
        HistoricalActivity("gas_refill", date(2026, 7, 30), 850),
        HistoricalActivity("gas_refill", date(2026, 8, 28), 900),
    ]

    pattern = Pattern(
        "gas_refill",
        29.0,
        True,
        "HIGH",
        [29, 29]
    )

    prediction = predict_next_occurrence(pattern, records)

    assert prediction.expected_next_date == date(2026, 9, 26)
    assert prediction.confidence == "HIGH"


def test_no_prediction_when_not_recurring():
    pattern = Pattern(
        "subscription",
        None,
        False,
        "LOW",
        []
    )

    prediction = predict_next_occurrence(pattern, [])

    assert prediction.expected_next_date is None


def test_context_becomes_relevant_near_date():
    prediction = Prediction(
        "gas_refill",
        date(2026, 9, 26),
        "HIGH"
    )

    state = Responsibility(
        "gas_refill",
        "DUE",
        date(2026, 9, 26),
        0.6,
        0.5
    )

    context = build_context(
        prediction,
        state,
        today=date(2026, 9, 24)
    )

    assert context.is_becoming_relevant is True
    assert context.days_until_predicted == 2


def test_context_not_relevant_when_far_away():
    prediction = Prediction(
        "gas_refill",
        date(2026, 9, 26),
        "HIGH"
    )

    state = Responsibility(
        "gas_refill",
        "UPCOMING",
        date(2026, 9, 26),
        0.2,
        0.2
    )

    context = build_context(
        prediction,
        state,
        today=date(2026, 9, 1)
    )

    assert context.is_becoming_relevant is False