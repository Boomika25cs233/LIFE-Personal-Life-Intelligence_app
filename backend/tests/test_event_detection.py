from app.services.event_detection import detect_event_type


def test_detect_electricity_bill():
    text = "your electricity bill of ₹2500 is due tomorrow"

    result = detect_event_type(text)

    assert result["event_type"] == "ELECTRICITY_BILL"
    assert len(result["matched_keywords"]) > 0


def test_detect_emi_payment():
    text = "your emi payment of ₹5600 is due on 5 september"

    result = detect_event_type(text)

    assert result["event_type"] == "EMI_PAYMENT"
    assert len(result["matched_keywords"]) > 0


def test_detect_order_shipped():
    text = "your order has been shipped"

    result = detect_event_type(text)

    assert result["event_type"] == "ORDER_SHIPPED"
    assert len(result["matched_keywords"]) > 0


def test_detect_refund_received():
    text = "refund of ₹750 received in your account"

    result = detect_event_type(text)

    assert result["event_type"] == "REFUND_RECEIVED"


def test_unknown_event():
    text = "hello have a nice day"

    result = detect_event_type(text)

    assert result["event_type"] == "UNKNOWN_EVENT"
    assert result["matched_keywords"] == []
    assert result["score"] == 0


def test_tie_break_is_deterministic():
    text = "your electricity bill is due, also your order has been shipped"

    result1 = detect_event_type(text)
    result2 = detect_event_type(text)

    assert result1["event_type"] == result2["event_type"]
    assert result1["event_type"] == "ELECTRICITY_BILL"