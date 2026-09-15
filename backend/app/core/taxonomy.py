"""
Controlled vocabulary for LIFE events.

RULES:
- event_type values are the ONLY valid event types the system may output.
- category values are the ONLY valid categories.
- Never invent new event_type strings elsewhere in the codebase.
  Add them here first.
"""


# ============================================================
# EVENT TYPE -> CATEGORY
# ============================================================

EVENT_CATEGORY_MAP = {

    # FINANCE
    "ELECTRICITY_BILL": "FINANCE",
    "CREDIT_CARD_BILL": "FINANCE",
    "EMI_PAYMENT": "FINANCE",
    "RENT_PAYMENT": "FINANCE",
    "SUBSCRIPTION_PAYMENT": "FINANCE",
    "GENERIC_PAYMENT": "FINANCE",

    # MEDICAL
    "MEDICINE_REFILL": "MEDICAL",
    "DOCTOR_APPOINTMENT": "MEDICAL",
    "MEDICAL_REPORT": "MEDICAL",

    # EDUCATION
    "EXAM": "EDUCATION",
    "ASSIGNMENT": "EDUCATION",
    "FEE_PAYMENT": "EDUCATION",

    # HOUSEHOLD
    "GAS_PURCHASE": "HOUSEHOLD",
    "MILK_DELIVERY": "HOUSEHOLD",
    "GROCERY": "HOUSEHOLD",

    # TRANSPORT
    "FUEL": "TRANSPORT",
    "VEHICLE_SERVICE": "TRANSPORT",
    "VEHICLE_INSURANCE": "TRANSPORT",

    # SHOPPING
    "ORDER_PLACED": "SHOPPING",
    "ORDER_SHIPPED": "SHOPPING",
    "ORDER_DELIVERED": "SHOPPING",
    "REFUND_INITIATED": "SHOPPING",
    "REFUND_RECEIVED": "SHOPPING",

    # WAITING
    "WAITING_REFUND": "WAITING",
    "WAITING_REPLACEMENT": "WAITING",
    "WAITING_CERTIFICATE": "WAITING",
    "WAITING_TECHNICIAN": "WAITING",
    "WAITING_REPAYMENT": "WAITING",

    # WARRANTY
    "WARRANTY_REGISTRATION": "WARRANTY",
    "WARRANTY_EXPIRY": "WARRANTY",

    # FALLBACK
    "UNKNOWN_EVENT": "UNKNOWN",
}


# ============================================================
# EVENT TYPE -> RESPONSIBILITY
# ============================================================

EVENT_RESPONSIBILITY_MAP = {

    # FINANCE
    "ELECTRICITY_BILL": "BILL_PAYMENT",
    "CREDIT_CARD_BILL": "BILL_PAYMENT",
    "EMI_PAYMENT": "EMI_PAYMENT",
    "RENT_PAYMENT": "BILL_PAYMENT",
    "SUBSCRIPTION_PAYMENT": "BILL_PAYMENT",
    "GENERIC_PAYMENT": "BILL_PAYMENT",

    # MEDICAL
    "MEDICINE_REFILL": "BUY_MEDICINE",
    "DOCTOR_APPOINTMENT": "ATTEND_APPOINTMENT",
    "MEDICAL_REPORT": "COLLECT_REPORT",

    # EDUCATION
    "EXAM": "ATTEND_EXAM",
    "ASSIGNMENT": "SUBMIT_ASSIGNMENT",
    "FEE_PAYMENT": "BILL_PAYMENT",

    # HOUSEHOLD
    "GAS_PURCHASE": "BUY_GAS",
    "MILK_DELIVERY": "PAY_OR_MANAGE_DELIVERY",
    "GROCERY": "BUY_GROCERY",

    # TRANSPORT
    "FUEL": "REFUEL_VEHICLE",
    "VEHICLE_SERVICE": "SERVICE_VEHICLE",
    "VEHICLE_INSURANCE": "INSURANCE_RENEWAL",

    # SHOPPING
    "ORDER_PLACED": "TRACK_ORDER",
    "ORDER_SHIPPED": "RECEIVE_ORDER",
    "ORDER_DELIVERED": "NO_ACTION_NEEDED",
    "REFUND_INITIATED": "WAIT_FOR_REFUND",
    "REFUND_RECEIVED": "NO_ACTION_NEEDED",

    # WAITING
    "WAITING_REFUND": "WAIT_FOR_REFUND",
    "WAITING_REPLACEMENT": "WAIT_FOR_REPLACEMENT",
    "WAITING_CERTIFICATE": "WAIT_FOR_CERTIFICATE",
    "WAITING_TECHNICIAN": "WAIT_FOR_TECHNICIAN",
    "WAITING_REPAYMENT": "WAIT_FOR_REPAYMENT",

    # WARRANTY
    "WARRANTY_REGISTRATION": "REGISTER_WARRANTY",
    "WARRANTY_EXPIRY": "REVIEW_WARRANTY",

    # FALLBACK
    "UNKNOWN_EVENT": "REVIEW_MANUALLY",
}


# ============================================================
# EVENT TYPE -> KEYWORDS
# ============================================================

EVENT_KEYWORDS = {

    # --------------------------------------------------------
    # FINANCE
    # --------------------------------------------------------

    "ELECTRICITY_BILL": [
        "electricity bill",
        "electricity board",
        "power bill",
    ],

    "CREDIT_CARD_BILL": [
        "credit card bill",
        "card statement",
        "card payment due",
    ],

    "EMI_PAYMENT": [
        "emi",
        "installment due",
        "loan installment",
    ],

    "RENT_PAYMENT": [
        "rent due",
        "rent payment",
        "monthly rent",
    ],

    "SUBSCRIPTION_PAYMENT": [
        "subscription",
        "renewal due",
        "auto-renewal",
    ],

    "GENERIC_PAYMENT": [
        "payment due",
        "payment required",
        "amount due",
    ],

    # --------------------------------------------------------
    # MEDICAL
    # --------------------------------------------------------

    "MEDICINE_REFILL": [
        "medicine refill",
        "refill your prescription",
        "prescription due",
        "prescription for",
        "refill",
    ],

    "DOCTOR_APPOINTMENT": [
        "doctor appointment",
        "appointment with dr",
        "consultation scheduled",
    ],

    "MEDICAL_REPORT": [
        "medical report",
        "test report ready",
        "lab report",
        "test results",
        "diagnostic test results",
        "diagnostic results",
    ],

    # --------------------------------------------------------
    # EDUCATION
    # --------------------------------------------------------

    "EXAM": [
        "exam",
        "examination",
        "test scheduled",
    ],

    "ASSIGNMENT": [
        "assignment due",
        "submit assignment",
        "homework due",
    ],

    "FEE_PAYMENT": [
        "fee payment",
        "college fee",
        "school fee",
        "tuition due",
    ],

    # --------------------------------------------------------
    # HOUSEHOLD
    # --------------------------------------------------------

    "GAS_PURCHASE": [
        "gas cylinder",
        "gas booking",
        "lpg",
    ],

    "MILK_DELIVERY": [
        "milk delivery",
        "milk subscription",
    ],

    "GROCERY": [
        "grocery order",
        "grocery delivery",
    ],

    # --------------------------------------------------------
    # TRANSPORT
    # --------------------------------------------------------

    "FUEL": [
        "fuel",
        "petrol",
        "diesel",
    ],

    "VEHICLE_SERVICE": [
        "vehicle service",
        "car service due",
        "bike service",
        "bike service reminder",
    ],

    "VEHICLE_INSURANCE": [
        "vehicle insurance",
        "car insurance",
        "policy renewal",
    ],

    # --------------------------------------------------------
    # SHOPPING
    # --------------------------------------------------------

    "ORDER_PLACED": [
        "order placed",
        "order confirmed",
    ],

    "ORDER_SHIPPED": [
        "order shipped",
        "has been shipped",
        "out for delivery",
        "courier",
        "parcel",
    ],

    "ORDER_DELIVERED": [
        "order delivered",
        "has been delivered",
    ],

    "REFUND_INITIATED": [
        "refund initiated",
        "refund processed",
        "refund of",
    ],

    "REFUND_RECEIVED": [
        "refund received",
        "refund credited",
        "refund received in",
    ],

    # --------------------------------------------------------
    # WAITING
    # --------------------------------------------------------

    "WAITING_REFUND": [
        "refund pending",
        "waiting for refund",
        "refund not received",
    ],

    "WAITING_REPLACEMENT": [
        "replacement requested",
        "replacement will be",
        "replacement order",
        "replacement confirmed",
    ],

    "WAITING_CERTIFICATE": [
        "certificate will be",
        "certificate is pending",
        "awaiting certificate",
    ],

    "WAITING_TECHNICIAN": [
        "technician will visit",
        "technician assigned",
        "service technician",
    ],

    "WAITING_REPAYMENT": [
        "repayment pending",
        "amount will be credited back",
    ],

    # --------------------------------------------------------
    # WARRANTY
    # --------------------------------------------------------

    "WARRANTY_REGISTRATION": [
        "register your warranty",
        "warranty registration",
    ],

    "WARRANTY_EXPIRY": [
        "warranty expiring",
        "warranty expires",
        "warranty ending",
        "warranty is expiring",
        "warranty is ending",
    ],
}


# ============================================================
# HELPER FUNCTIONS
# ============================================================

def get_category(event_type: str) -> str:
    """Returns the controlled category for a given event_type."""
    return EVENT_CATEGORY_MAP.get(
        event_type,
        "UNKNOWN"
    )


def get_responsibility(event_type: str) -> str:
    """Returns the controlled responsibility type for a given event_type."""
    return EVENT_RESPONSIBILITY_MAP.get(
        event_type,
        "REVIEW_MANUALLY"
    )


def is_valid_event_type(event_type: str) -> bool:
    """
    Guards against accidentally introducing an event_type
    not present in our taxonomy.
    """
    return event_type in EVENT_CATEGORY_MAP