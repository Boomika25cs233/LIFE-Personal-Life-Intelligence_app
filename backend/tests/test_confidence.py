from app.services.confidence import calculate_confidence


def test_no_evidence_gives_zero_confidence():
    result = calculate_confidence([])

    assert result["confidence"] == 0.0
    assert result["state"] == "UNKNOWN"


def test_sender_and_event_type_confirmed():
    result = calculate_confidence([
        "SENDER_PREVIOUSLY_CONFIRMED",
        "EVENT_TYPE_PREVIOUSLY_CONFIRMED",
        "KNOWN_EVENT_CATEGORY",
    ])

    assert result["confidence"] == 0.65
    assert result["state"] == "LIKELY"


def test_known_category_only_stays_unknown():
    result = calculate_confidence([
        "KNOWN_EVENT_CATEGORY"
    ])

    assert result["confidence"] == 0.05
    assert result["state"] == "UNKNOWN"


def test_all_evidence_maxes_at_one():
    result = calculate_confidence([
        "SENDER_PREVIOUSLY_CONFIRMED",
        "EVENT_TYPE_PREVIOUSLY_CONFIRMED",
        "ACCOUNT_IDENTIFIER_MATCH",
        "RECURRING_SIMILAR_EVIDENCE",
        "KNOWN_EVENT_CATEGORY",
    ])

    assert result["confidence"] == 1.0
    assert result["state"] == "CONFIRMED"


def test_unknown_flag_is_ignored_not_error():
    result = calculate_confidence([
        "SOME_MADE_UP_FLAG"
    ])

    assert result["confidence"] == 0.0


def test_contributing_factors_recorded():
    result = calculate_confidence([
        "SENDER_PREVIOUSLY_CONFIRMED"
    ])

    assert result["contributing_factors"] == [
        {
            "factor": "SENDER_PREVIOUSLY_CONFIRMED",
            "weight": 0.30
        }
    ]


def test_confirmed_event_with_account_match():
    result = calculate_confidence([
        "EVENT_TYPE_PREVIOUSLY_CONFIRMED",
        "ACCOUNT_IDENTIFIER_MATCH",
        "KNOWN_EVENT_CATEGORY",
    ])

    assert result["confidence"] == 0.50
    assert result["state"] == "POSSIBLE"