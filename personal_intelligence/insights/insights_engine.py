"""
LIFE Insights Engine
Converts Josi's intelligence results into human-readable messages
suitable for display in Boomika's UI.
"""

import os
import sys
from typing import Optional

sys.path.append(os.path.join(os.path.dirname(__file__), "..", "models"))
from models import Insight, Pattern, Anomaly, Prediction, Baseline


def _friendly_name(responsibility: str) -> str:
    """Turn 'electricity_bill' -> 'electricity bill' for nicer sentences."""
    return responsibility.replace("_", " ")


def generate_insight(
    responsibility: str,
    pattern: Optional[Pattern] = None,
    baseline: Optional[Baseline] = None,
    anomaly: Optional[Anomaly] = None,
    prediction: Optional[Prediction] = None
) -> Insight:
    """
    Build one clear message, prioritizing the most important thing to tell the user:
      1. Anomaly (if present) - most urgent
      2. Prediction (if recurring) - helpful heads-up
      3. Pattern info - general observation
      4. Fallback - generic message
    """
    name = _friendly_name(responsibility)

    if anomaly and anomaly.is_anomaly:
        message = f"Your {name} is higher than usual." if "higher" in anomaly.reason \
            else f"Your {name} looks different from your usual pattern."
        return Insight(responsibility=responsibility, message=message)

    if prediction and prediction.expected_next_date:
        message = (
            f"You usually handle your {name} every "
            f"{pattern.average_interval_days:.0f} days. "
            f"Your next one may be due around {prediction.expected_next_date.strftime('%B %d')}."
        )
        return Insight(responsibility=responsibility, message=message)

    if pattern and pattern.is_recurring:
        message = f"You usually handle your {name} every {pattern.average_interval_days:.0f} days."
        return Insight(responsibility=responsibility, message=message)

    return Insight(responsibility=responsibility, message=f"No unusual activity detected for your {name}.")


if __name__ == "__main__":
    from datetime import date

    # Case 1: Anomaly present -> anomaly insight
    anomaly = Anomaly(responsibility="electricity_bill", is_anomaly=True,
                       anomaly_score=0.91, reason="significantly higher than baseline")
    insight1 = generate_insight("electricity_bill", anomaly=anomaly)
    print(insight1.message)

    # Case 2: Recurring pattern + prediction -> prediction insight
    pattern = Pattern(responsibility="gas_refill", average_interval_days=29.0,
                       is_recurring=True, confidence="HIGH", intervals=[29, 29])
    prediction = Prediction(responsibility="gas_refill", expected_next_date=date(2026, 9, 26),
                             confidence="HIGH")
    insight2 = generate_insight("gas_refill", pattern=pattern, prediction=prediction)
    print(insight2.message)

    # Case 3: No anomaly, no prediction -> fallback
    insight3 = generate_insight("groceries")
    print(insight3.message)