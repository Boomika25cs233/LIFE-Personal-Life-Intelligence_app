"""
Configurable weights for the Confidence Engine.

Weights are kept in one place so scoring behavior
can be tuned without changing the scoring algorithm.
"""

CONFIDENCE_WEIGHTS = {
    "SENDER_PREVIOUSLY_CONFIRMED": 0.30,
    "EVENT_TYPE_PREVIOUSLY_CONFIRMED": 0.30,
    "ACCOUNT_IDENTIFIER_MATCH": 0.15,
    "RECURRING_SIMILAR_EVIDENCE": 0.20,
    "KNOWN_EVENT_CATEGORY": 0.05,
}


CONFIDENCE_THRESHOLDS = [
    (0.81, 1.00, "CONFIRMED"),
    (0.61, 0.80, "LIKELY"),
    (0.31, 0.60, "POSSIBLE"),
    (0.00, 0.30, "UNKNOWN"),
]


def score_to_state(score: float) -> str:
    """Maps a numeric confidence score to an ownership state."""

    for low, high, state in CONFIDENCE_THRESHOLDS:
        if low <= score <= high:
            return state

    return "UNKNOWN"