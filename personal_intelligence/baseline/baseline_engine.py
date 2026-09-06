"""
Personal Baseline Engine
Calculates the 'normal' range for a responsibility's amount based on history.
"""

import statistics
import os
import sys
from typing import List

sys.path.append(os.path.join(os.path.dirname(__file__), "..", "models"))
from models import Baseline, HistoricalActivity


def calculate_baseline(responsibility: str, records: List[HistoricalActivity]) -> Baseline:
    """
    Build a personal baseline from historical amounts.

    Normal range = average ± 1 standard deviation.
    This is a simple, explainable statistical method (no ML needed):
    it defines "normal" as the range most historical values actually fall into.
    """
    amounts = [r.amount for r in records if r.amount is not None]

    if not amounts:
        return Baseline(
            responsibility=responsibility,
            average=0, median=0, minimum=0, maximum=0,
            std_dev=0, normal_range_low=0, normal_range_high=0
        )

    average = statistics.mean(amounts)
    median = statistics.median(amounts)
    minimum = min(amounts)
    maximum = max(amounts)
    std_dev = statistics.pstdev(amounts) if len(amounts) > 1 else 0

    return Baseline(
        responsibility=responsibility,
        average=round(average, 2),
        median=round(median, 2),
        minimum=minimum,
        maximum=maximum,
        std_dev=round(std_dev, 2),
        normal_range_low=round(average - std_dev, 2),
        normal_range_high=round(average + std_dev, 2)
    )


if __name__ == "__main__":
    sys.path.append(os.path.join(os.path.dirname(__file__), "..", "data"))
    from historical_processor import load_historical_data

    data = load_historical_data("electricity.json", responsibility_filter="electricity_bill")
    baseline = calculate_baseline("electricity_bill", data)

    print(f"Average: {baseline.average}")
    print(f"Median: {baseline.median}")
    print(f"Min: {baseline.minimum}")
    print(f"Max: {baseline.maximum}")
    print(f"Std Dev: {baseline.std_dev}")
    print(f"Normal range: {baseline.normal_range_low} - {baseline.normal_range_high}")

    # Test against a new value
    current = 2850
    print(f"\nCurrent value = {current}")
    if current > baseline.normal_range_high:
        print("This is ABOVE the normal range.")
    elif current < baseline.normal_range_low:
        print("This is BELOW the normal range.")
    else:
        print("This is WITHIN the normal range.")