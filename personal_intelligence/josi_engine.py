"""
Master Josi Engine
Coordinates all Josi components into a single pipeline:

INPUT EVENT
   -> Historical Data
   -> Pattern Detection
   -> Personal Baseline
   -> Anomaly Detection
   -> Prediction
   -> Context
   -> Relevance Score
   -> Notification Decision
   -> Personal Learning (feedback penalty applied)
   -> Insight
   -> FINAL OUTPUT
"""

import os
import sys
from datetime import date
from typing import Optional

# Make every submodule importable
BASE = os.path.dirname(__file__)
for sub in ["models", "data", "patterns", "baseline", "anomaly",
            "prediction", "context", "scoring", "notification",
            "learning", "insights"]:
    sys.path.append(os.path.join(BASE, sub))

from models import Event, Responsibility, Insight  # noqa: E402
from historical_processor import load_historical_data  # noqa: E402
from pattern_detector import detect_pattern  # noqa: E402
from baseline_engine import calculate_baseline  # noqa: E402
from anomaly_detector import detect_anomaly  # noqa: E402
from prediction_engine import predict_next_occurrence  # noqa: E402
from context_engine import build_context  # noqa: E402
from relevance_engine import calculate_relevance  # noqa: E402
from notification_engine import decide_notification  # noqa: E402
from learning_engine import get_feedback_penalty, get_last_ignored_date, is_completed_today  # noqa: E402
from insights_engine import generate_insight  # noqa: E402


class JosiEngine:
    def __init__(self, sample_data_filename: str):
        """
        sample_data_filename: which sample_data/*.json file holds history
        for this responsibility (e.g. "electricity.json").
        """
        self.sample_data_filename = sample_data_filename

    def process_event(
        self,
        event: Event,
        responsibility_state: Optional[Responsibility],
        today: date
    ) -> dict:
        resp = event.responsibility if event.responsibility else event.event_type
        # Historical data is keyed by event_type in our sample files (e.g. "electricity_bill")
        history_key = event.event_type

        # 1. Load + clean historical data
        history = load_historical_data(self.sample_data_filename, responsibility_filter=history_key)

        # 2. Pattern detection
        pattern = detect_pattern(history_key, history)

        # 3. Baseline
        baseline = calculate_baseline(history_key, history)

        # 4. Anomaly detection (compare new event's amount to baseline)
        anomaly = None
        if event.amount is not None:
            anomaly = detect_anomaly(history_key, event.amount, baseline)

        # 5. Prediction
        prediction = predict_next_occurrence(pattern, history)

        # 6. Context
        context = build_context(prediction, responsibility_state, today)

        # 7. Learning lookups (feedback penalty + cooldown info)
        feedback_penalty = get_feedback_penalty(history_key)
        last_ignored_date = get_last_ignored_date(history_key)
        completed_today = is_completed_today(history_key, today)

        # 8. Relevance scoring
        relevance = calculate_relevance(pattern, context, anomaly, feedback_penalty=feedback_penalty)

        # 9. Notification decision
        last_ignored_map = {history_key: last_ignored_date} if last_ignored_date else {}
        notification = decide_notification(
            relevance, today,
            last_ignored=last_ignored_map,
            already_completed=completed_today
        )

        # 10. Insight (human-readable message)
        insight = generate_insight(
            history_key, pattern=pattern, baseline=baseline,
            anomaly=anomaly, prediction=prediction
        )

        # 11. Final structured output
        return {
            "event": history_key,
            "anomaly": anomaly.is_anomaly if anomaly else False,
            "anomaly_score": anomaly.anomaly_score if anomaly else 0.0,
            "prediction": prediction.expected_next_date.isoformat() if prediction.expected_next_date else None,
            "relevance": relevance.level,
            "notification_action": notification.action,
            "insight": insight.message
        }


if __name__ == "__main__":
    engine = JosiEngine(sample_data_filename="electricity.json")

    print("=== ELECTRICITY BILL EXAMPLE ===")
    event = Event(
        event_type="electricity_bill",
        amount=2850,
        date=date(2026, 9, 1),
        category="finance",
        responsibility="bill_payment",
        ownership_confidence=0.92
    )
    resp_state = Responsibility(
        responsibility="electricity_bill", state="DUE",
        due_date=date(2026, 9, 5), importance=0.8, urgency=0.7
    )
    result = engine.process_event(event, resp_state, today=date(2026, 9, 1))
    for k, v in result.items():
        print(f"{k}: {v}")

    print("\n=== GAS REFILL PREDICTION EXAMPLE ===")
    engine2 = JosiEngine(sample_data_filename="gas.json")
    event2 = Event(
        event_type="gas_refill",
        amount=None,
        date=date(2026, 9, 24),
        category="utility",
        responsibility="gas_refill",
        ownership_confidence=0.95
    )
    resp_state2 = Responsibility(
        responsibility="gas_refill", state="UPCOMING",
        due_date=date(2026, 9, 26), importance=0.6, urgency=0.5
    )
    result2 = engine2.process_event(event2, resp_state2, today=date(2026, 9, 24))
    for k, v in result2.items():
        print(f"{k}: {v}")