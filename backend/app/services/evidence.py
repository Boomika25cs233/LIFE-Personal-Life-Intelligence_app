from typing import List, Dict, Any

from app.core.evidence_templates import flag_to_sentence


def generate_evidence(
    contributing_factors: List[Dict[str, Any]]
) -> List[str]:
    """
    Converts the Confidence Engine's contributing_factors
    (flag + weight pairs) into an ordered list of human-readable
    evidence sentences.
    """

    evidence_sentences = []

    for item in contributing_factors:
        sentence = flag_to_sentence(item["factor"])
        evidence_sentences.append(sentence)

    return evidence_sentences