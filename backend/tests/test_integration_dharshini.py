def test_dharshini_can_detect_then_retrieve_event(
    db_session,
    client_factory
):
    client = client_factory(db_session)

    response = client.post(
        "/api/v1/events/detect",
        json={
            "source": "SMS",
            "text": (
                "HDFC Bank: Your EMI payment of "
                "₹5600 is due on 5 September."
            ),
        },
    )

    assert response.status_code == 200

    data = response.json()

    # Detection result
    assert data["event_id"] is not None
    assert data["event_type"] == "EMI_PAYMENT"
    assert data["category"] == "FINANCE"
    assert data["responsibility"] == "EMI_PAYMENT"

    # Extracted entities
    assert data["entities"]["amount"] == 5600.0
    assert data["entities"]["currency"] == "INR"
    assert data["entities"]["sender"] == "HDFC Bank"

    # Retrieve the same event
    event_id = data["event_id"]

    get_response = client.get(
        f"/api/v1/events/{event_id}"
    )

    assert get_response.status_code == 200

    retrieved = get_response.json()

    assert retrieved["event_id"] == event_id
    assert retrieved["event_type"] == "EMI_PAYMENT"
    assert retrieved["category"] == "FINANCE"
    assert retrieved["responsibility"] == "EMI_PAYMENT"
    assert retrieved["amount"] == 5600.0
    assert retrieved["currency"] == "INR"
    assert retrieved["sender"] == "HDFC Bank"


def test_dharshini_can_filter_actionable_events_by_ownership(
    db_session,
    client_factory
):
    client = client_factory(db_session)

    # First event creates the sender record.
    first_response = client.post(
        "/api/v1/events/detect",
        json={
            "source": "SMS",
            "text": (
                "Electricity Board: Your electricity bill "
                "of ₹2850 is due tomorrow."
            ),
        },
    )

    assert first_response.status_code == 200

    first_data = first_response.json()
    first_event_id = first_data["event_id"]

    assert first_event_id is not None

    # User confirms the first event.
    feedback_response = client.post(
        "/api/v1/events/feedback",
        json={
            "event_id": first_event_id,
            "feedback_type": "CONFIRM",
        },
    )

    assert feedback_response.status_code == 200
    assert feedback_response.json()["success"] is True

    # A new event from the same sender should now have
    # sender-confirmed evidence.
    second_response = client.post(
        "/api/v1/events/detect",
        json={
            "source": "SMS",
            "text": (
                "Electricity Board: Your electricity bill "
                "of ₹2950 is due tomorrow."
            ),
        },
    )

    assert second_response.status_code == 200

    second_data = second_response.json()

    assert second_data["event_id"] is not None
    assert second_data["ownership"] == "POSSIBLE"
    assert second_data["confidence"] == 0.35

    # Dharshini can request only POSSIBLE events.
    list_response = client.get(
        "/api/v1/events?ownership=POSSIBLE"
    )

    assert list_response.status_code == 200

    events = list_response.json()

    assert len(events) >= 1

    for event in events:
        assert event["ownership_state"] == "POSSIBLE"

    assert any(
        event["event_id"] == second_data["event_id"]
        for event in events
    )