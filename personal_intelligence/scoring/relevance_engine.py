"""
Relevance / Priority Engine
Combines confidence, importance, urgency, context, and anomaly level
into a single relevance score (HIGH / MEDIUM / LOW).
"""

import os
import sys
from typing import Optional

sys.path.append(os.path.join(os.path.dirname(__file__), "..", "models"))
from models import RelevanceScore, Pattern, Context, Anomaly


# Weights for each factor - kept simple and explainable (no ML needed).
# Together they sum to 1.0, so the final score naturally lands between 0 and 1.
WEIGHTS = {
    "confidence": 0.20,
    "importance": 0.20,
    "urgency": 0.20,
    "context": 0.20,
    "anomaly": 0.20,
}

CONFIDENCE_TO_SCORE = {"HIGH": 1.0, "MEDIUM": 0.6, "LOW": 0.2}


def calculate_relevance(
    pattern: Optional[Pattern],
    context: Context,
    anomaly: Optional[Anomaly],
    feedback_penalty: float = 0.0
) -> RelevanceScore:
    """
    Combine multiple signals into one relevance score.

    feedback_penalty: 0.0 - 1.0, reduces score if the user has recently
    marked similar suggestions as NOT_RELEVANT or IGNORE (see Phase 13).
    """
    confidence_score = CONFIDENCE_TO_SCORE.get(pattern.confidence, 0.2) if pattern else 0.2
    importance_score = context.importance
    urgency_score = context.urgency
    context_score = 1.0 if context.is_becoming_relevant else 0.2
    anomaly_score = anomaly.anomaly_score if anomaly else 0.0

    raw_score = (
        WEIGHTS["confidence"] * confidence_score +
        WEIGHTS["importance"] * importance_score +
        WEIGHTS["urgency"] * urgency_score +
        WEIGHTS["context"] * context_score +
        WEIGHTS["anomaly"] * anomaly_score
    )

    final_score = max(0.0, raw_score - feedback_penalty)

    if final_score >= 0.65:
        level = "HIGH"
    elif final_score >= 0.35:
        level = "MEDIUM"
    else:
        level = "LOW"

    return RelevanceScore(
        responsibility=context.responsibility,
        score=round(final_score, 2),
        level=level
    )


if __name__ == "__main__":
    from datetime import date
    from models import Pattern, Context, Anomaly

    # Test Case 1: strong pattern, becoming relevant, real anomaly -> should be HIGH
    pattern = Pattern(responsibility="electricity_bill", average_interval_days=30,
                       is_recurring=True, confidence="HIGH", intervals=[30, 30])
    context = Context(responsibility="electricity_bill", today=date(2026, 9, 1),
                       predicted_date=date(2026, 9, 5), days_until_predicted=4,
                       urgency=0.7, importance=0.8, is_becoming_relevant=True)
    anomaly = Anomaly(responsibility="electricity_bill", is_anomaly=True,
                       anomaly_score=0.91, reason="Higher than normal")

    result = calculate_relevance(pattern, context, anomaly)
    print(f"Test 1 (strong signals): score={result.score}, level={result.level}")

    # Test Case 2: weak pattern, not yet relevant, no anomaly -> should be LOW
    pattern2 = Pattern(responsibility="subscription", average_interval_days=30,
                        is_recurring=False, confidence="LOW", intervals=[10, 50])
    context2 = Context(responsibility="subscription", today=date(2026, 9, 1),
                        predicted_date=date(2026, 10, 1), days_until_predicted=30,
                        urgency=0.1, importance=0.2, is_becoming_relevant=False)
    anomaly2 = Anomaly(responsibility="subscription", is_anomaly=False,
                        anomaly_score=0.05, reason="Normal")

    result2 = calculate_relevance(pattern2, context2, anomaly2)
    print(f"Test 2 (weak signals): score={result2.score}, level={result2.level}")

    # Test Case 3: medium signals -> should be MEDIUM
    pattern3 = Pattern(responsibility="fuel", average_interval_days=10,
                        is_recurring=True, confidence="MEDIUM", intervals=[9, 11])
    context3 = Context(responsibility="fuel", today=date(2026, 9, 1),
                        predicted_date=date(2026, 9, 8), days_until_predicted=7,
                        urgency=0.4, importance=0.4, is_becoming_relevant=False)
    anomaly3 = Anomaly(responsibility="fuel", is_anomaly=False,
                        anomaly_score=0.2, reason="Slightly different")

    result3 = calculate_relevance(pattern3, context3, anomaly3)
    print(f"Test 3 (medium signals): score={result3.score}, level={result3.level}")