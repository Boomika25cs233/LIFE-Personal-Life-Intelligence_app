from app.models.event_model import EventDB


def _seed_events(db_session):
    events = [
        EventDB(
            event_id="EVT-001",
            event_type="ELECTRICITY_BILL",
            category="FINANCE",
            responsibility="BILL_PAYMENT",
            ownership_state="LIKELY",
            confidence=0.65,
            fingerprint="fp1",
        ),
        EventDB(
            event_id="EVT-002",
            event_type="EMI_PAYMENT",
            category="FINANCE",
            responsibility="EMI_PAYMENT",
            ownership_state="POSSIBLE",
            confidence=0.05,
            fingerprint="fp2",
        ),
        EventDB(
            event_id="EVT-003",
            event_type="DOCTOR_APPOINTMENT",
            category="MEDICAL",
            responsibility="ATTEND_APPOINTMENT",
            ownership_state="UNKNOWN",
            confidence=0.0,
            fingerprint="fp3",
        ),
    ]

    db_session.add_all(events)
    db_session.commit()


def test_get_single_event_found(db_session, client_factory):
    _seed_events(db_session)

    client = client_factory(db_session)

    response = client.get("/api/v1/events/EVT-001")

    assert response.status_code == 200
    assert response.json()["event_type"] == "ELECTRICITY_BILL"


def test_get_single_event_not_found(db_session, client_factory):
    _seed_events(db_session)

    client = client_factory(db_session)

    response = client.get("/api/v1/events/EVT-DOES-NOT-EXIST")

    assert response.status_code == 404


def test_list_all_events(db_session, client_factory):
    _seed_events(db_session)

    client = client_factory(db_session)

    response = client.get("/api/v1/events")

    assert response.status_code == 200
    assert len(response.json()) == 3


def test_list_filtered_by_category(db_session, client_factory):
    _seed_events(db_session)

    client = client_factory(db_session)

    response = client.get("/api/v1/events?category=FINANCE")

    data = response.json()

    assert response.status_code == 200
    assert len(data) == 2
    assert all(e["category"] == "FINANCE" for e in data)


def test_list_filtered_by_ownership(db_session, client_factory):
    _seed_events(db_session)

    client = client_factory(db_session)

    response = client.get("/api/v1/events?ownership=UNKNOWN")

    data = response.json()

    assert response.status_code == 200
    assert len(data) == 1
    assert data[0]["event_id"] == "EVT-003"