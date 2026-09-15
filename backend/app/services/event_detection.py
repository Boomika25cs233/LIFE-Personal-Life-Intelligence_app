from typing import Dict, Any, List

from app.core.taxonomy import EVENT_KEYWORDS


# Priority order used ONLY to break ties when multiple event types
# receive the same specificity score.
TIE_BREAK_PRIORITY = [
    "EMI_PAYMENT",
    "CREDIT_CARD_BILL",
    "RENT_PAYMENT",
    "ELECTRICITY_BILL",
    "SUBSCRIPTION_PAYMENT",
    "FEE_PAYMENT",
    "DOCTOR_APPOINTMENT",
    "MEDICINE_REFILL",
    "MEDICAL_REPORT",
    "EXAM",
    "ASSIGNMENT",
    "VEHICLE_INSURANCE",
    "VEHICLE_SERVICE",
    "FUEL",
    "WARRANTY_EXPIRY",
    "WARRANTY_REGISTRATION",
    "REFUND_INITIATED",
    "REFUND_RECEIVED",
    "WAITING_REPLACEMENT",
    "WAITING_CERTIFICATE",
    "WAITING_TECHNICIAN",
    "WAITING_REPAYMENT",
    "ORDER_SHIPPED",
    "ORDER_PLACED",
    "ORDER_DELIVERED",
    "GAS_PURCHASE",
    "GROCERY",
    "MILK_DELIVERY",
]


def detect_event_type(text: str) -> Dict[str, Any]:
    """
    Detects the most likely event type from the cleaned message.

    Matching rules:
    1. Longer/more specific phrases receive higher scores.
    2. Multiple matching keywords receive a small bonus.
    3. If event types have the same score, use TIE_BREAK_PRIORITY.
    4. If no event matches, return UNKNOWN_EVENT.
    """

    if not text:
        return {
            "event_type": "UNKNOWN_EVENT",
            "matched_keywords": [],
            "score": 0
        }

    # ---------------------------------------------------------
    # Explicit refund-received rule
    # ---------------------------------------------------------
    if (
        "refund" in text
        and (
            "refund received" in text
            or "received in your account" in text
            or "refund credited" in text
            or "credited to your account" in text
        )
    ):
        return {
            "event_type": "REFUND_RECEIVED",
            "matched_keywords": ["refund received"],
            "score": len("refund received") + 10
        }

    scores: Dict[str, Dict[str, Any]] = {}

    # ---------------------------------------------------------
    # Normal taxonomy matching
    # ---------------------------------------------------------
    for event_type, keywords in EVENT_KEYWORDS.items():
        matched = [kw for kw in keywords if kw in text]

        if matched:
            # Longer phrases are more specific.
            specificity_score = sum(len(kw) for kw in matched)

            # Small bonus for multiple matching keywords.
            score = specificity_score + (len(matched) * 10)

            scores[event_type] = {
                "matched_keywords": matched,
                "score": score
            }

    if not scores:
        return {
            "event_type": "UNKNOWN_EVENT",
            "matched_keywords": [],
            "score": 0
        }

    # ---------------------------------------------------------
    # Find highest score
    # ---------------------------------------------------------
    max_score = max(
        data["score"]
        for data in scores.values()
    )

    tied_event_types = [
        event_type
        for event_type, data in scores.items()
        if data["score"] == max_score
    ]

    # ---------------------------------------------------------
    # Select winner
    # ---------------------------------------------------------
    if len(tied_event_types) == 1:
        winner = tied_event_types[0]

    else:
        # Deterministic tie-breaking.
        ranked = sorted(
            tied_event_types,
            key=lambda event_type: (
                TIE_BREAK_PRIORITY.index(event_type)
                if event_type in TIE_BREAK_PRIORITY
                else len(TIE_BREAK_PRIORITY),
                event_type,
            ),
        )

        winner = ranked[0]

    return {
        "event_type": winner,
        "matched_keywords": scores[winner]["matched_keywords"],
        "score": scores[winner]["score"]
    }