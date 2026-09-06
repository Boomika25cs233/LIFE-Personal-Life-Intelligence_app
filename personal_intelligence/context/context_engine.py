"""
Context Engine
Combines prediction timing with Dharshini's responsibility state
(urgency, importance) to determine if something is becoming relevant.
"""

import os
import sys
from datetime import date
from typing import Optional

sys.path.append(os.path.join(os.path.dirname(__file__), "..", "models"))
from models import Context, Prediction, Responsibility


# How many days before the predicted date we start considering it "becoming relevant"
RELEVANCE_WINDOW_DAYS = 5


def build_context(
    prediction: Prediction,
    responsibility_state: Optional[Responsibility],
    today: date
) -> Context:
    """
    Build context by comparing today's date to the predicted date,
    and factoring in urgency/importance from Dharshini.
    """
    predicted_date = prediction.expected_next_date

    days_until_predicted = None
    is_becoming_relevant = False

    if predicted_date is not None:
        days_until_predicted = (predicted_date - today).days
        # Becomes relevant if predicted date is close (within window) or already passed
        if days_until_predicted <= RELEVANCE_WINDOW_DAYS:
            is_becoming_relevant = True

    urgency = responsibility_state.urgency if responsibility_state else 0.0
    importance = responsibility_state.importance if responsibility_state else 0.0

    # High urgency/importance from Dharshini can also flag relevance directly
    if urgency >= 0.7 or importance >= 0.7:
        is_becoming_relevant = True

    return Context(
        responsibility=prediction.responsibility,
        today=today,
        predicted_date=predicted_date,
        days_until_predicted=days_until_predicted,
        urgency=urgency,
        importance=importance,
        is_becoming_relevant=is_becoming_relevant
    )


if __name__ == "__main__":
    from models import Responsibility

    # Simulate Phase 9's prediction output
    prediction = Prediction(
        responsibility="gas_refill",
        expected_next_date=date(2026, 9, 26),
        confidence="HIGH"
    )

    # Simulate Dharshini's input
    responsibility_state = Responsibility(
        responsibility="gas_refill",
        state="DUE",
        due_date=date(2026, 9, 26),
        importance=0.6,
        urgency=0.5
    )

    today = date(2026, 9, 24)

    context = build_context(prediction, responsibility_state, today)

    print(f"Expected refill = {context.predicted_date}")
    print(f"Today = {context.today}")
    print(f"Days until predicted = {context.days_until_predicted}")
    print(f"Urgency = {context.urgency}, Importance = {context.importance}")
    print(f"Is becoming relevant = {context.is_becoming_relevant}")