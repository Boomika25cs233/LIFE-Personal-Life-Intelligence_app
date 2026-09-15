import re


def preprocess_text(raw_text: str) -> str:
    """
    Cleans and normalizes raw input text for downstream processing.

    Does NOT destroy information needed for entity extraction
    such as numbers, currency symbols, and dates.
    """

    if not raw_text:
        return ""

    text = raw_text.lower()

    # Collapse repeated punctuation.
    text = re.sub(
        r'([!?.,])\1+',
        r'\1',
        text
    )

    # Normalize multiple spaces.
    text = re.sub(
        r'\s+',
        ' ',
        text
    )

    text = text.strip()

    return text