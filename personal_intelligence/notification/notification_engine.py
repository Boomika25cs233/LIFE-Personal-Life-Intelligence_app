"""
Smart Notification Engine
Decides NOTIFY / SUGGEST / SILENT based on relevance,
while filtering out duplicates and recently-ignored suggestions.
"""

import os
import sys
from datetime import date, timedelta
from typing import Optional, Dict

sys.path.append(os.path.join(os.path.dirname(__file__), "..", "models"))
from models import NotificationDecision, RelevanceScore


# How many days to suppress a responsibility after the user ignored/rejected it
COOLDOWN_DAYS = 3


def decide_notification(
    relevance: RelevanceScore,
    today: date,
    last_notified: Optional[Dict[str, date]] = None,
    last_ignored: Optional[Dict[str, date]] = None,
    already_completed: bool = False
) -> NotificationDecision:
    """
    Map relevance level -> action, with duplicate/cooldown/completion guards.

    last_notified: dict of {responsibility: date_last_notified}
    last_ignored: dict of {responsibility: date_last_ignored_or_rejected}
    """
    last_notified = last_notified or {}
    last_ignored = last_ignored or {}
    resp = relevance.responsibility

    # Guard 1: already completed -> always silent
    if already_completed:
        return NotificationDecision(
            responsibility=resp,
            action="SILENT",
            reason="Responsibility already marked complete."
        )

    # Guard 2: recently ignored/rejected -> stay silent during cooldown
    if resp in last_ignored:
        days_since_ignored = (today - last_ignored[resp]).days
        if days_since_ignored < COOLDOWN_DAYS:
            return NotificationDecision(
                responsibility=resp,
                action="SILENT",
                reason=f"User recently ignored/rejected this ({days_since_ignored} days ago); in cooldown."
            )

    # Guard 3: already notified today -> avoid duplicate notification
    if resp in last_notified and last_notified[resp] == today:
        return NotificationDecision(
            responsibility=resp,
            action="SILENT",
            reason="Already notified about this today."
        )

    # Base mapping from relevance level
    if relevance.level == "HIGH":
        return NotificationDecision(responsibility=resp, action="NOTIFY",
                                     reason="High relevance score.")
    elif relevance.level == "MEDIUM":
        return NotificationDecision(responsibility=resp, action="SUGGEST",
                                     reason="Medium relevance score.")
    else:
        return NotificationDecision(responsibility=resp, action="SILENT",
                                     reason="Low relevance score.")


if __name__ == "__main__":
    from models import RelevanceScore

    today = date(2026, 9, 1)

    # Case 1: HIGH relevance, no history -> NOTIFY
    r1 = RelevanceScore(responsibility="electricity_bill", score=0.9, level="HIGH")
    print(decide_notification(r1, today))

    # Case 2: MEDIUM relevance -> SUGGEST
    r2 = RelevanceScore(responsibility="fuel", score=0.5, level="MEDIUM")
    print(decide_notification(r2, today))

    # Case 3: HIGH relevance but already notified today -> SILENT (duplicate guard)
    r3 = RelevanceScore(responsibility="gas_refill", score=0.8, level="HIGH")
    print(decide_notification(r3, today, last_notified={"gas_refill": today}))

    # Case 4: HIGH relevance but recently ignored -> SILENT (cooldown guard)
    r4 = RelevanceScore(responsibility="subscription", score=0.7, level="HIGH")
    print(decide_notification(r4, today, last_ignored={"subscription": today - timedelta(days=1)}))

    # Case 5: already completed -> SILENT
    r5 = RelevanceScore(responsibility="rent", score=0.95, level="HIGH")
    print(decide_notification(r5, today, already_completed=True))