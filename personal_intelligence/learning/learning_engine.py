"""
Personal Learning Engine
Records user feedback and maintains learned state per responsibility,
so future relevance scoring and notifications adapt to user behavior.
"""

import json
import os
from datetime import date, datetime
from typing import Dict, Optional


import sys
sys.path.append(os.path.join(os.path.dirname(__file__), "..", "models"))
from models import Feedback


LEARNING_STORE_PATH = os.path.join("personal_intelligence", "learning", "learning_state.json")


def _load_state() -> Dict:
    if not os.path.exists(LEARNING_STORE_PATH):
        return {}
    try:
        with open(LEARNING_STORE_PATH, "r") as f:
            return json.load(f)
    except json.JSONDecodeError:
        return {}


def _save_state(state: Dict):
    os.makedirs(os.path.dirname(LEARNING_STORE_PATH), exist_ok=True)
    with open(LEARNING_STORE_PATH, "w") as f:
        json.dump(state, f, indent=2, default=str)


def record_feedback(feedback: Feedback) -> Dict:
    """
    Update the learning store based on user feedback.

    Effects per feedback_type:
      CONFIRM       -> increases trust in this responsibility's pattern,
                        clears any ignore-cooldown.
      NOT_RELEVANT  -> records rejection, adds a feedback_penalty for scoring,
                        sets last_ignored.
      IGNORE        -> similar to NOT_RELEVANT but smaller penalty.
      COMPLETE      -> marks responsibility as completed for now.
    """
    state = _load_state()
    resp = feedback.responsibility
    entry = state.get(resp, {
        "confirm_count": 0,
        "not_relevant_count": 0,
        "ignore_count": 0,
        "last_ignored": None,
        "last_completed": None,
        "feedback_penalty": 0.0
    })

    if feedback.feedback_type == "CONFIRM":
        entry["confirm_count"] += 1
        entry["last_ignored"] = None
        entry["feedback_penalty"] = max(0.0, entry["feedback_penalty"] - 0.1)

    elif feedback.feedback_type == "NOT_RELEVANT":
        entry["not_relevant_count"] += 1
        entry["last_ignored"] = str(feedback.date)
        entry["feedback_penalty"] = min(1.0, entry["feedback_penalty"] + 0.3)

    elif feedback.feedback_type == "IGNORE":
        entry["ignore_count"] += 1
        entry["last_ignored"] = str(feedback.date)
        entry["feedback_penalty"] = min(1.0, entry["feedback_penalty"] + 0.15)

    elif feedback.feedback_type == "COMPLETE":
        entry["last_completed"] = str(feedback.date)

    state[resp] = entry
    _save_state(state)
    return entry


def get_feedback_penalty(responsibility: str) -> float:
    state = _load_state()
    return state.get(responsibility, {}).get("feedback_penalty", 0.0)


def get_last_ignored_date(responsibility: str) -> Optional[date]:
    state = _load_state()
    raw = state.get(responsibility, {}).get("last_ignored")
    if raw:
        return datetime.strptime(raw, "%Y-%m-%d").date()
    return None


def is_completed_today(responsibility: str, today: date) -> bool:
    state = _load_state()
    raw = state.get(responsibility, {}).get("last_completed")
    if raw:
        return datetime.strptime(raw, "%Y-%m-%d").date() == today
    return False


if __name__ == "__main__":
    today = date(2026, 9, 1)

    print("--- Recording CONFIRM ---")
    fb1 = Feedback(responsibility="gas_refill", feedback_type="CONFIRM", date=today)
    print(record_feedback(fb1))

    print("\n--- Recording NOT_RELEVANT ---")
    fb2 = Feedback(responsibility="subscription_netflix", feedback_type="NOT_RELEVANT", date=today)
    print(record_feedback(fb2))

    print("\n--- Recording IGNORE ---")
    fb3 = Feedback(responsibility="fuel", feedback_type="IGNORE", date=today)
    print(record_feedback(fb3))

    print("\n--- Recording COMPLETE ---")
    fb4 = Feedback(responsibility="electricity_bill", feedback_type="COMPLETE", date=today)
    print(record_feedback(fb4))

    print("\n--- Reading back state ---")
    print(f"Penalty for subscription_netflix: {get_feedback_penalty('subscription_netflix')}")
    print(f"Last ignored (fuel): {get_last_ignored_date('fuel')}")
    print(f"electricity_bill completed today: {is_completed_today('electricity_bill', today)}")