"""
Recurring Pattern Detector
Analyzes date gaps to determine if a responsibility recurs regularly.
"""

import statistics
import os
import sys
from typing import List

sys.path.append(os.path.join(os.path.dirname(__file__), "..", "models"))
sys.path.append(os.path.join(os.path.dirname(__file__), "..", "data"))
from models import Pattern, HistoricalActivity
from historical_processor import calculate_date_gaps


def detect_pattern(responsibility: str, records: List[HistoricalActivity]) -> Pattern:
    """
    Determine whether a responsibility recurs at a regular interval.

    Confidence rule (simple, explainable — no ML needed):
      - Needs at least 2 gaps (3 data points) to judge consistency.
      - We measure how much gaps vary using standard deviation.
      - Low variation relative to the average -> HIGH confidence.
      - Moderate variation -> MEDIUM confidence.
      - High variation or too little data -> LOW confidence.
    """
    gaps = calculate_date_gaps(records)

    if len(gaps) < 2:
        return Pattern(
            responsibility=responsibility,
            average_interval_days=gaps[0] if gaps else None,
            is_recurring=False,
            confidence="LOW",
            intervals=gaps
        )

    average = statistics.mean(gaps)
    std_dev = statistics.pstdev(gaps)

    # Coefficient of variation: how "wobbly" the gaps are relative to their size
    variation_ratio = std_dev / average if average > 0 else 1.0

    if variation_ratio <= 0.10:
        confidence = "HIGH"
        is_recurring = True
    elif variation_ratio <= 0.30:
        confidence = "MEDIUM"
        is_recurring = True
    else:
        confidence = "LOW"
        is_recurring = False

    return Pattern(
        responsibility=responsibility,
        average_interval_days=round(average, 1),
        is_recurring=is_recurring,
        confidence=confidence,
        intervals=gaps
    )


if __name__ == "__main__":
    from historical_processor import load_historical_data

    data = load_historical_data("gas.json", responsibility_filter="gas_refill")
    pattern = detect_pattern("gas_refill", data)

    print(f"Intervals: {pattern.intervals}")
    print(f"Average interval: {pattern.average_interval_days} days")
    print(f"Pattern detected: {'YES' if pattern.is_recurring else 'NO'}")
    print(f"Confidence: {pattern.confidence}")