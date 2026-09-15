def test_boomika_receives_display_ready_explanation(
    db_session,
    client_factory
):
    client = client_factory(db_session)

    response = client.post(
        "/api/v1/events/detect",
        json={
            "source": "SMS",
            "text": (
                "Electricity Board: Your electricity bill "
                "of ₹2400 is due tomorrow."
            ),
        },
    )

    assert response.status_code == 200

    data = response.json()

    # Basic display information
    assert data["event_type"] == "ELECTRICITY_BILL"
    assert data["category"] == "FINANCE"
    assert data["responsibility"] == "BILL_PAYMENT"

    # Explanation must be available for the UI.
    assert isinstance(data["explanation"], str)
    assert len(data["explanation"]) > 0

    # Evidence must be available separately.
    assert isinstance(data["evidence"], list)
    assert len(data["evidence"]) > 0

    # Explanation should contain user-friendly language,
    # not internal implementation flags.
    assert "SENDER_PREVIOUSLY_CONFIRMED" not in data["explanation"]
    assert "KNOWN_EVENT_CATEGORY" not in data["explanation"]


def test_boomika_can_display_confidence_as_percentage(
    db_session,
    client_factory
):
    client = client_factory(db_session)

    response = client.post(
        "/api/v1/events/detect",
        json={
            "source": "SMS",
            "text": (
                "Your grocery payment of ₹1500 "
                "is due tomorrow."
            ),
        },
    )

    assert response.status_code == 200

    data = response.json()

    confidence = data["confidence"]

    # Backend confidence is represented as 0.0 - 1.0.
    assert isinstance(confidence, (int, float))
    assert 0.0 <= confidence <= 1.0

    # Boomika can convert it to a percentage for display.
    confidence_percentage = confidence * 100

    assert 0 <= confidence_percentage <= 100