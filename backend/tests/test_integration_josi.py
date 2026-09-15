def test_josi_can_retrieve_historical_events(
    db_session,
    client_factory
):
    client = client_factory(db_session)

    messages = [
        {
            "source": "SMS",
            "text": (
                "Electricity Board: Your electricity bill "
                "of ₹1850 is due tomorrow."
            ),
            "event_type": "ELECTRICITY_BILL",
            "category": "FINANCE",
        },
        {
            "source": "SMS",
            "text": (
                "HDFC Bank: Your EMI payment of "
                "₹5600 is due on 10 September."
            ),
            "event_type": "EMI_PAYMENT",
            "category": "FINANCE",
        },
        {
            "source": "SMS",
            "text": (
                "Your rent payment of ₹18000 is due tomorrow."
            ),
            "event_type": "RENT_PAYMENT",
            "category": "FINANCE",
        },
    ]

    created_event_ids = []

    for message in messages:
        response = client.post(
            "/api/v1/events/detect",
            json={
                "source": message["source"],
                "text": message["text"],
            },
        )

        assert response.status_code == 200

        data = response.json()

        assert data["event_id"] is not None
        assert data["event_type"] == message["event_type"]
        assert data["category"] == message["category"]

        created_event_ids.append(data["event_id"])

    # Retrieve all historical events.
    list_response = client.get(
        "/api/v1/events"
    )

    assert list_response.status_code == 200

    events = list_response.json()

    assert len(events) >= 3

    returned_ids = {
        event["event_id"]
        for event in events
    }

    for event_id in created_event_ids:
        assert event_id in returned_ids


def test_josi_can_filter_events_by_category(
    db_session,
    client_factory
):
    client = client_factory(db_session)

    finance_response = client.post(
        "/api/v1/events/detect",
        json={
            "source": "SMS",
            "text": (
                "Electricity Board: Your electricity bill "
                "of ₹2100 is due tomorrow."
            ),
        },
    )

    assert finance_response.status_code == 200

    medical_response = client.post(
        "/api/v1/events/detect",
        json={
            "source": "SMS",
            "text": (
                "Doctor appointment scheduled for "
                "tomorrow at 10 AM."
            ),
        },
    )

    assert medical_response.status_code == 200

    # Filter only finance events.
    response = client.get(
        "/api/v1/events?category=FINANCE"
    )

    assert response.status_code == 200

    events = response.json()

    assert len(events) >= 1

    for event in events:
        assert event["category"] == "FINANCE"

    assert any(
        event["event_id"] == finance_response.json()["event_id"]
        for event in events
    )

    # Filter only medical events.
    response = client.get(
        "/api/v1/events?category=MEDICAL"
    )

    assert response.status_code == 200

    medical_events = response.json()

    assert len(medical_events) >= 1

    for event in medical_events:
        assert event["category"] == "MEDICAL"

    assert any(
        event["event_id"] == medical_response.json()["event_id"]
        for event in medical_events
    )