from app.services.explainability import generate_explanation


def test_explanation_with_single_evidence():
    result = generate_explanation(
        "POSSIBLE",
        ["This matches a known, recognizable event category"]
    )

    assert result["explanation"] == (
        "This might be yours. Here's why we think so: "
        "this matches a known, recognizable event category."
    )


def test_explanation_with_multiple_evidence():
    result = generate_explanation(
        "LIKELY",
        [
            "Same sender as a previously confirmed event",
            "This matches a known, recognizable event category",
        ]
    )

    assert "same sender as a previously confirmed event" in result["explanation"]
    assert "and this matches a known" in result["explanation"]


def test_explanation_no_evidence_fallback():
    result = generate_explanation("UNKNOWN", [])

    assert "don't have enough information" in result["explanation"]
    assert "confirm or reject" in result["explanation"]


def test_explanation_unknown_state_defaults_safely():
    result = generate_explanation(
        "SOME_INVALID_STATE",
        []
    )

    assert "not sure" in result["explanation"] or "confirm" in result["explanation"]


def test_no_technical_jargon_leaks():
    result = generate_explanation(
        "LIKELY",
        ["Same sender as a previously confirmed event"]
    )

    assert "SENDER_PREVIOUSLY_CONFIRMED" not in result["explanation"]