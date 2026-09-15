"""
Templates for user-facing explanations, keyed by ownership state.

Design rationale:
- Explanation TONE depends on ownership STATE.
- Kept deliberately simple and rule-based.
"""

STATE_INTRO = {
    "CONFIRMED": "This is confirmed as yours because:",
    "LIKELY": "This looks like it's likely yours because:",
    "POSSIBLE": "This might be yours. Here's why we think so:",
    "UNKNOWN": "We're not sure this belongs to you yet.",
    "LEARNED": "This appears to be yours based on what we've learned before:",
}

NO_EVIDENCE_FALLBACK = (
    "We don't have enough information yet to know if this is yours. "
    "You can confirm or reject it to help us learn."
)


def build_explanation(
    ownership_state: str,
    evidence_sentences: list
) -> str:
    """
    Builds a single, user-friendly explanation string from the
    ownership state and evidence sentences.
    """

    intro = STATE_INTRO.get(
        ownership_state,
        STATE_INTRO["UNKNOWN"]
    )

    if not evidence_sentences:
        return NO_EVIDENCE_FALLBACK

    lowered = [
        s[0].lower() + s[1:]
        for s in evidence_sentences
    ]

    if len(lowered) == 1:
        evidence_clause = lowered[0]
    else:
        evidence_clause = (
            ", ".join(lowered[:-1])
            + ", and "
            + lowered[-1]
        )

    return f"{intro} {evidence_clause}."