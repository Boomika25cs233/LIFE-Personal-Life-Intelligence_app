from app.services.confidence import calculate_confidence


def test_confidence_exactly_at_threshold_boundary():
    result = calculate_confidence(["KNOWN_EVENT_CATEGORY"])

    assert result["confidence"] == 0.05
    assert result["state"] == "UNKNOWN"


def test_confidence_score_never_negative():
    result = calculate_confidence([])

    assert result["confidence"] >= 0.0


def test_confidence_score_never_exceeds_one():
    result = calculate_confidence([
        "SENDER_PREVIOUSLY_CONFIRMED",
        "SENDER_PREVIOUSLY_CONFIRMED",
        "EVENT_TYPE_PREVIOUSLY_CONFIRMED",
        "ACCOUNT_IDENTIFIER_MATCH",
        "RECURRING_SIMILAR_EVIDENCE",
        "KNOWN_EVENT_CATEGORY",
    ])

    assert result["confidence"] <= 1.0