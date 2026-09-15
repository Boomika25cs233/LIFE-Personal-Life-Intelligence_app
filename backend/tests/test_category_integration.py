from app.services.event_detection import detect_event_type
from app.core.taxonomy import get_category


def test_electricity_bill_maps_to_finance():
    text = "your electricity bill of 2850 is due tomorrow"
    detection = detect_event_type(text)
    category = get_category(detection["event_type"])
    assert category == "FINANCE"


def test_doctor_appointment_maps_to_medical():
    text = "you have an appointment with dr. sharma tomorrow"
    detection = detect_event_type(text)
    category = get_category(detection["event_type"])
    assert category == "MEDICAL"


def test_order_shipped_maps_to_shopping():
    text = "your order has been shipped and is out for delivery"
    detection = detect_event_type(text)
    category = get_category(detection["event_type"])
    assert category == "SHOPPING"


def test_unknown_text_maps_to_unknown_category():
    text = "hey are we still meeting for coffee later"
    detection = detect_event_type(text)
    category = get_category(detection["event_type"])
    assert category == "UNKNOWN"