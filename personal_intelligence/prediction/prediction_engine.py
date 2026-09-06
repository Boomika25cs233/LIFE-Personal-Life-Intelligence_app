"""
Prediction Engine
Predicts the next expected date for a recurring responsibility,
using the Pattern produced by the Pattern Detector (Phase 6).
"""

import os
import sys
from datetime import timedelta
from typing import List

sys.path.append(os.path.join(os.path.dirname(__file__), "..", "models"))
from models import Prediction, Pattern, HistoricalActivity


def predict_next_occurrence(pattern: Pattern, records: List[HistoricalActivity]) -> Prediction:
    """
    Predict the next date a recurring responsibility is expected.

    Rule (simple, explainable):
      - If the pattern isn't recurring, or there's no history, we can't predict.
      - Otherwise: next date = last known date + average interval.
      - Confidence is carried over directly from the pattern's confidence,
        since prediction reliability depends entirely on pattern consistency.
    """
    if not pattern.is_recurring or not records or pattern.average_interval_days is None:
        return Prediction(
            responsibility=pattern.responsibility,
            expected_next_date=None,
            confidence="LOW"
        )

    last_date = records[-1].date
    expected_next_date = last_date + timedelta(days=round(pattern.average_interval_days))

    return Prediction(
        responsibility=pattern.responsibility,
        expected_next_date=expected_next_date,
        confidence=pattern.confidence
    )


if __name__ == "__main__":
    sys.path.append(os.path.join(os.path.dirname(__file__), "..", "data"))
    sys.path.append(os.path.join(os.path.dirname(__file__), "..", "patterns"))
    from historical_processor import load_historical_data
    from pattern_detector import detect_pattern

    data = load_historical_data("gas.json", responsibility_filter="gas_refill")
    pattern = detect_pattern("gas_refill", data)
    prediction = predict_next_occurrence(pattern, data)

    print(f"Average interval = {pattern.average_interval_days} days")
    print(f"Last known date = {data[-1].date}")
    print(f"Expected next date = {prediction.expected_next_date}")
    print(f"Prediction confidence = {prediction.confidence}")