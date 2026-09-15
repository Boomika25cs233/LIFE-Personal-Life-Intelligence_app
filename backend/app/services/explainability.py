from typing import List, Dict, Any

from app.core.explanation_templates import build_explanation


def generate_explanation(
    ownership_state: str,
    evidence_sentences: List[str]
) -> Dict[str, Any]:
    """
    Produces the final user-facing explanation object.

    Returns:
        {
            "explanation": str,
            "evidence": list
        }
    """

    explanation_text = build_explanation(
        ownership_state,
        evidence_sentences
    )

    return {
        "explanation": explanation_text,
        "evidence": evidence_sentences,
    }