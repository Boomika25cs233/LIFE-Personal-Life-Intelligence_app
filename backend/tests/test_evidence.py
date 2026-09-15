from app.services.evidence import generate_evidence


def test_single_factor_translated():
    factors = [
        {
            "factor": "SENDER_PREVIOUSLY_CONFIRMED",
            "weight": 0.30
        }
    ]

    result = generate_evidence(factors)

    assert result == [
        "Same sender as a previously confirmed event"
    ]


def test_multiple_factors_translated_in_order():
    factors = [
        {
            "factor": "SENDER_PREVIOUSLY_CONFIRMED",
            "weight": 0.30
        },
        {
            "factor": "KNOWN_EVENT_CATEGORY",
            "weight": 0.05
        },
    ]

    result = generate_evidence(factors)

    assert result == [
        "Same sender as a previously confirmed event",
        "This matches a known, recognizable event category",
    ]


def test_empty_factors_gives_empty_evidence():
    assert generate_evidence([]) == []


def test_unrecognized_flag_does_not_crash():
    factors = [
        {
            "factor": "SOME_NEW_UNMAPPED_FLAG",
            "weight": 0.10
        }
    ]

    result = generate_evidence(factors)

    assert "SOME_NEW_UNMAPPED_FLAG" in result[0]