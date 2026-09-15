from app.services.event_detection import detect_event_type
from app.core.taxonomy import get_responsibility


def test_electricity_bill_responsibility():
    detection = detect_event_type(
        "your electricity bill of 2850 is due tomorrow"
    )
    assert get_responsibility(detection["event_type"]) == "BILL_PAYMENT"


def test_emi_responsibility():
    detection = detect_event_type(
        "your emi is due on 5th"
    )
    assert get_responsibility(detection["event_type"]) == "EMI_PAYMENT"


def test_order_shipped_responsibility():
    detection = detect_event_type(
        "your order has been shipped and is out for delivery"
    )
    assert get_responsibility(detection["event_type"]) == "RECEIVE_ORDER"


def test_doctor_appointment_responsibility():
    detection = detect_event_type(
        "you have an appointment with dr. sharma tomorrow"
    )
    assert get_responsibility(detection["event_type"]) == "ATTEND_APPOINTMENT"


def test_unknown_event_responsibility_fallback():
    detection = detect_event_type(
        "hey want to grab lunch"
    )
    assert get_responsibility(detection["event_type"]) == "REVIEW_MANUALLY"