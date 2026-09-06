"""
Complete End-to-End Demo
Demonstrates Josi's full pipeline across 3 real scenarios:
  A. Electricity bill anomaly
  B. Gas refill prediction
  C. User feedback changing future behavior
"""

import os
import sys
from datetime import date

BASE = os.path.dirname(__file__)
for sub in ["models", "data", "patterns", "baseline", "anomaly",
            "prediction", "context", "scoring", "notification",
            "learning", "insights"]:
    sys.path.append(os.path.join(BASE, sub))

from models import Event, Responsibility, Feedback  # noqa: E402
from josi_engine import JosiEngine  # noqa: E402
from learning_engine import record_feedback, get_feedback_penalty  # noqa: E402


def print_result(title, result):
    print(f"\n--- {title} ---")
    for k, v in result.items():
        print(f"{k}: {v}")


def case_a_electricity_anomaly():
    engine = JosiEngine(sample_data_filename="electricity.json")
    event = Event(
        event_type="electricity_bill", amount=2850, date=date(2026, 9, 1),
        category="finance", responsibility="bill_payment", ownership_confidence=0.92
    )
    state = Responsibility(
        responsibility="electricity_bill", state="DUE",
        due_date=date(2026, 9, 5), importance=0.8, urgency=0.7
    )
    result = engine.process_event(event, state, today=date(2026, 9, 1))
    print_result("CASE A: Electricity Bill Anomaly", result)
    return result


def case_b_gas_prediction():
    engine = JosiEngine(sample_data_filename="gas.json")
    event = Event(
        event_type="gas_refill", amount=None, date=date(2026, 9, 24),
        category="utility", responsibility="gas_refill", ownership_confidence=0.95
    )
    state = Responsibility(
        responsibility="gas_refill", state="UPCOMING",
        due_date=date(2026, 9, 26), importance=0.6, urgency=0.5
    )
    result = engine.process_event(event, state, today=date(2026, 9, 24))
    print_result("CASE B: Gas Refill Prediction", result)
    return result


def case_c_feedback_learning():
    print("\n--- CASE C: Feedback-Driven Learning ---")

    penalty_before = get_feedback_penalty("subscription_netflix")
    print(f"Feedback penalty BEFORE feedback: {penalty_before}")

    feedback = Feedback(
        responsibility="subscription_netflix", feedback_type="NOT_RELEVANT",
        date=date(2026, 9, 1)
    )
    record_feedback(feedback)

    penalty_after = get_feedback_penalty("subscription_netflix")
    print(f"Feedback penalty AFTER 'NOT_RELEVANT' feedback: {penalty_after}")
    print("This penalty will now reduce this responsibility's relevance score")
    print("the next time Josi evaluates it (see Phase 11: Relevance Engine).")


if __name__ == "__main__":
    print("=" * 60)
    print("LIFE - JOSI PERSONAL INTELLIGENCE - END-TO-END DEMO")
    print("=" * 60)

    case_a_electricity_anomaly()
    case_b_gas_prediction()
    case_c_feedback_learning()

    print("\n" + "=" * 60)
    print("DEMO COMPLETE")
    print("=" * 60)