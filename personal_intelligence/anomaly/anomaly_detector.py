"""
Anomaly Detector
Uses z-score (standard deviations from the mean) to detect unusual values.
"""

import os
import sys

sys.path.append(os.path.join(os.path.dirname(__file__), "..", "models"))
from models import Anomaly, Baseline


def detect_anomaly(responsibility: str, current_value: float, baseline: Baseline) -> Anomaly:
    """
    Compare current_value against the baseline using a z-score.
    """
    if baseline.std_dev == 0:
        # No variation in history to compare against
        is_anomaly = current_value != baseline.average
        return Anomaly(
            responsibility=responsibility,
            is_anomaly=is_anomaly,
            anomaly_score=1.0 if is_anomaly else 0.0,
            reason="No historical variation to compare against; value differs from the single known amount."
            if is_anomaly else "Matches the only known historical value."
        )

    z_score = (current_value - baseline.average) / baseline.std_dev
    abs_z = abs(z_score)

    # Convert z-score into a 0.0-1.0 anomaly score (capped at 1.0)
    anomaly_score = min(abs_z / 3, 1.0)

    if abs_z < 1:
        is_anomaly = False
        reason = f"{responsibility} amount is within the user's normal range."
    elif abs_z < 2:
        is_anomaly = True
        direction = "higher" if z_score > 0 else "lower"
        reason = f"Current {responsibility} is somewhat {direction} than the user's normal baseline."
    else:
        is_anomaly = True
        direction = "higher" if z_score > 0 else "lower"
        reason = f"Current {responsibility} is significantly {direction} than the user's normal baseline."

    return Anomaly(
        responsibility=responsibility,
        is_anomaly=is_anomaly,
        anomaly_score=round(anomaly_score, 2),
        reason=reason
    )


if __name__ == "__main__":
    sys.path.append(os.path.join(os.path.dirname(__file__), "..", "data"))
    sys.path.append(os.path.join(os.path.dirname(__file__), "..", "baseline"))
    from historical_processor import load_historical_data
    from baseline_engine import calculate_baseline

    data = load_historical_data("electricity.json", responsibility_filter="electricity_bill")
    baseline = calculate_baseline("electricity_bill", data)

    current = 2850
    anomaly = detect_anomaly("electricity_bill", current, baseline)

    print(f"Anomaly detected: {'YES' if anomaly.is_anomaly else 'NO'}")
    print(f"Anomaly score: {anomaly.anomaly_score}")
    print(f"Reason: {anomaly.reason}")