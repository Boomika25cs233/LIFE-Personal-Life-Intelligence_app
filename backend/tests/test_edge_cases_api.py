def test_missing_text_field_returns_422(db_session, client_factory):
    client = client_factory(db_session)

    response = client.post(
        "/api/v1/events/detect",
        json={"source": "SMS"}
    )

    assert response.status_code == 422


def test_missing_source_field_returns_422(db_session, client_factory):
    client = client_factory(db_session)

    response = client.post(
        "/api/v1/events/detect",
        json={"text": "hello"}
    )

    assert response.status_code == 422


def test_wrong_type_for_text_returns_422(db_session, client_factory):
    client = client_factory(db_session)

    response = client.post(
        "/api/v1/events/detect",
        json={
            "source": "SMS",
            "text": 12345
        }
    )

    assert response.status_code == 422


def test_empty_json_body_returns_422(db_session, client_factory):
    client = client_factory(db_session)

    response = client.post(
        "/api/v1/events/detect",
        json={}
    )

    assert response.status_code == 422


def test_completely_invalid_json_body(db_session, client_factory):
    client = client_factory(db_session)

    response = client.post(
        "/api/v1/events/detect",
        data="this is not json at all",
        headers={"Content-Type": "application/json"},
    )

    assert response.status_code == 422


def test_extra_unexpected_fields_are_ignored(db_session, client_factory):
    """
    Pydantic ignores extra fields by default.
    The API should still accept the request.
    """
    client = client_factory(db_session)

    response = client.post(
        "/api/v1/events/detect",
        json={
            "source": "SMS",
            "text": "your electricity bill is due",
            "extra_field": "should be ignored",
        }
    )

    assert response.status_code == 200