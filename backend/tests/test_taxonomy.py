from app.core.taxonomy import (
    EVENT_CATEGORY_MAP,
    EVENT_RESPONSIBILITY_MAP,
    EVENT_KEYWORDS,
    get_category,
    get_responsibility,
    is_valid_event_type,
)


def test_every_keyword_event_has_category_and_responsibility():
    """
    Guards against taxonomy drift:
    every event_type that has keywords defined
    MUST also have a category and responsibility defined.
    """

    for event_type in EVENT_KEYWORDS:
        assert event_type in EVENT_CATEGORY_MAP, (
            f"{event_type} missing category"
        )

        assert event_type in EVENT_RESPONSIBILITY_MAP, (
            f"{event_type} missing responsibility"
        )


def test_get_category_known():
    assert get_category("ELECTRICITY_BILL") == "FINANCE"


def test_get_category_unknown_falls_back():
    assert get_category("SOMETHING_MADE_UP") == "UNKNOWN"


def test_get_responsibility_known():
    assert get_responsibility("EMI_PAYMENT") == "EMI_PAYMENT"


def test_is_valid_event_type():
    assert is_valid_event_type("ELECTRICITY_BILL") is True
    assert is_valid_event_type("RANDOM_MADE_UP_EVENT") is False