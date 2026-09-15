"""
Maps internal evidence flag names -> human-readable evidence sentences.

Design rationale:
- Keeping this separate from confidence_config.py (weights) means we can
  change WORDING without touching SCORING, and vice versa. Two different
  concerns, two different files.
- Templates are plain strings (no placeholders) for now, since our
  evidence flags are currently boolean-style ("this happened" / "it didn't").
  If we later need dynamic details (e.g., naming the exact sender), we can
  upgrade these to format strings — noted as a future improvement.
"""

EVIDENCE_TEMPLATES = {
    "SENDER_PREVIOUSLY_CONFIRMED": "Same sender as a previously confirmed event",
    "EVENT_TYPE_PREVIOUSLY_CONFIRMED": "You previously confirmed a similar event type",
    "ACCOUNT_IDENTIFIER_MATCH": "Account identifier matches a previous event",
    "RECURRING_SIMILAR_EVIDENCE": "This looks similar to a recurring event pattern",
    "KNOWN_EVENT_CATEGORY": "This matches a known, recognizable event category",
}


def flag_to_sentence(flag: str) -> str:
    """Converts one evidence flag into a human-readable sentence."""
    return EVIDENCE_TEMPLATES.get(
        flag,
        f"Unrecognized evidence signal: {flag}"
    )