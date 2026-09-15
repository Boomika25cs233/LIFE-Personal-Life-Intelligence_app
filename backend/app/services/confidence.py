from typing import Dict, Any, List

from app.core.confidence_config import (
    CONFIDENCE_WEIGHTS,
    score_to_state,
)


def calculate_confidence(
    evidence_flags: List[str]
) -> Dict[str, Any]:
    """
    Computes a numeric confidence score from evidence flags.

    Duplicate evidence flags are ignored so that the same
    evidence cannot increase the confidence score more than once.

    The score is clamped to the range 0.0 to 1.0.

    Returns:
        {
            "confidence": float,
            "state": str,
            "contributing_factors": list
        }
    """

    raw_score = 0.0
    contributing_factors: List[Dict[str, Any]] = []

    # Deduplicate while preserving the original order.
    seen = set()
    unique_flags = []

    for flag in evidence_flags:
        if flag not in seen:
            seen.add(flag)
            unique_flags.append(flag)

    for flag in unique_flags:
        weight = CONFIDENCE_WEIGHTS.get(flag)

        if weight is not None:
            raw_score += weight

            contributing_factors.append({
                "factor": flag,
                "weight": weight
            })

    clamped_score = max(0.0, min(1.0, raw_score))
    clamped_score = round(clamped_score, 2)

    state = score_to_state(clamped_score)

    return {
        "confidence": clamped_score,
        "state": state,
        "contributing_factors": contributing_factors,
    }