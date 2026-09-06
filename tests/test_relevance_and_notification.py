from datetime import date, timedelta

from models import (
    Pattern,
    Context,
    Anomaly,
    RelevanceScore
)

from relevance_engine import calculate_relevance
from notification_engine import decide_notification


def test_high_relevance():
    pattern = Pattern(
        "electricity_bill",
        30,
        True,
        "HIGH",
        [30, 30]
    )

    context = Context(
        "electricity_bill",
        date(2026, 9, 1),
        date(2026, 9, 5),
        4,
        0.7,
        0.8,
        True
    )

    anomaly = Anomaly(
        "electricity_bill",
        True,
        0.91,
        "higher than normal"
    )

    result = calculate_relevance(
        pattern,
        context,
        anomaly
    )

    assert result.level == "HIGH"


def test_low_relevance():
    pattern = Pattern(
        "subscription",
        30,
        False,
        "LOW",
        [10, 50]
    )

    context = Context(
        "subscription",
        date(2026, 9, 1),
        date(2026, 10, 1),
        30,
        0.1,
        0.2,
        False
    )

    anomaly = Anomaly(
        "subscription",
        False,
        0.05,
        "normal"
    )

    result = calculate_relevance(
        pattern,
        context,
        anomaly
    )

    assert result.level == "LOW"


def test_feedback_penalty_lowers_score():
    pattern = Pattern(
        "fuel",
        10,
        True,
        "HIGH",
        [10, 10]
    )

    context = Context(
        "fuel",
        date(2026, 9, 1),
        date(2026, 9, 8),
        5,
        0.7,
        0.7,
        True
    )

    anomaly = Anomaly(
        "fuel",
        True,
        0.6,
        "higher than normal"
    )

    without_penalty = calculate_relevance(
        pattern,
        context,
        anomaly,
        feedback_penalty=0.0
    )

    with_penalty = calculate_relevance(
        pattern,
        context,
        anomaly,
        feedback_penalty=0.5
    )

    assert with_penalty.score < without_penalty.score


def test_notify_on_high_relevance():
    relevance = RelevanceScore(
        "electricity_bill",
        0.9,
        "HIGH"
    )

    decision = decide_notification(
        relevance,
        today=date(2026, 9, 1)
    )

    assert decision.action == "NOTIFY"


def test_duplicate_notification_blocked():
    relevance = RelevanceScore(
        "gas_refill",
        0.8,
        "HIGH"
    )

    today = date(2026, 9, 1)

    decision = decide_notification(
        relevance,
        today,
        last_notified={"gas_refill": today}
    )

    assert decision.action == "SILENT"


def test_cooldown_blocks_recently_ignored():
    relevance = RelevanceScore(
        "subscription",
        0.7,
        "HIGH"
    )

    today = date(2026, 9, 1)

    decision = decide_notification(
        relevance,
        today,
        last_ignored={
            "subscription": today - timedelta(days=1)
        }
    )

    assert decision.action == "SILENT"


def test_completed_always_silent():
    relevance = RelevanceScore(
        "rent",
        0.95,
        "HIGH"
    )

    decision = decide_notification(
        relevance,
        date(2026, 9, 1),
        already_completed=True
    )

    assert decision.action == "SILENT"