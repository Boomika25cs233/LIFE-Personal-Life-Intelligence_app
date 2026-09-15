import re
from datetime import datetime, timedelta
from typing import Optional, Dict, Any


KNOWN_SENDER_PATTERNS = {
    "electricity board": "Electricity Board",
    "power corporation": "Electricity Board",
    "power grid": "Electricity Board",

    "hdfc": "HDFC Bank",
    "icici": "ICICI Bank",
    "sbi": "SBI",
    "axis bank": "Axis Bank",

    "amazon": "Amazon",
    "flipkart": "Flipkart",
    "myntra": "Myntra",

    "gas agency": "Gas Agency",
    "indane": "Gas Agency",
    "hp gas": "Gas Agency",

    "insurance": "Insurance Provider",
    "lic": "LIC",

    "netflix": "Netflix",
    "spotify": "Spotify",
}


def extract_sender(text: str) -> Optional[str]:
    for fragment, normalized_name in KNOWN_SENDER_PATTERNS.items():
        if fragment in text:
            return normalized_name

    return None


def extract_amount_and_currency(text: str) -> Dict[str, Any]:
    patterns = [
        (r'₹\s?([\d,]+(?:\.\d+)?)', "INR"),
        (r'rs\.?\s?([\d,]+(?:\.\d+)?)', "INR"),
        (r'inr\s?([\d,]+(?:\.\d+)?)', "INR"),
        (r'\$\s?([\d,]+(?:\.\d+)?)', "USD"),
        (r'usd\s?([\d,]+(?:\.\d+)?)', "USD"),
        (
            r'(?:bill|amount|payment)\s+of\s+₹?\s?([\d,]+(?:\.\d+)?)',
            "INR"
        ),
    ]

    for pattern, currency in patterns:
        match = re.search(
            pattern,
            text,
            re.IGNORECASE
        )

        if match:
            raw_number = match.group(1).replace(",", "")

            return {
                "amount": float(raw_number),
                "currency": currency
            }

    return {
        "amount": None,
        "currency": None
    }


def extract_date(
    text: str,
    reference_date: Optional[datetime] = None
) -> Optional[str]:

    if reference_date is None:
        reference_date = datetime.now()

    # ---------------------------------------------------------
    # Format: DD/MM/YYYY or DD-MM-YYYY
    # Example: 18/09/2026
    # Example: 18-09-2026
    # ---------------------------------------------------------
    match = re.search(
        r'(\d{1,2})[/-](\d{1,2})[/-](\d{4})',
        text
    )

    if match:
        day, month, year = match.groups()

        try:
            parsed = datetime(
                int(year),
                int(month),
                int(day)
            )

            return parsed.strftime("%Y-%m-%d")

        except ValueError:
            pass

    # ---------------------------------------------------------
    # Tomorrow
    # ---------------------------------------------------------
    if "tomorrow" in text:
        parsed = reference_date + timedelta(days=1)

        return parsed.strftime("%Y-%m-%d")

    # ---------------------------------------------------------
    # Today
    # ---------------------------------------------------------
    if "today" in text:
        return reference_date.strftime("%Y-%m-%d")

    # ---------------------------------------------------------
    # Next weekday
    # Examples:
    # "next monday"
    # "next friday"
    # ---------------------------------------------------------
    weekdays = {
        "monday": 0,
        "tuesday": 1,
        "wednesday": 2,
        "thursday": 3,
        "friday": 4,
        "saturday": 5,
        "sunday": 6,
    }

    next_weekday_match = re.search(
        r'next\s+(' + '|'.join(weekdays.keys()) + r')',
        text,
        re.IGNORECASE
    )

    if next_weekday_match:
        target_weekday = weekdays[
            next_weekday_match.group(1).lower()
        ]

        days_ahead = (
            target_weekday
            - reference_date.weekday()
            + 7
        ) % 7

        # If today is the target weekday,
        # "next Monday" means the following week's Monday.
        if days_ahead == 0:
            days_ahead = 7

        parsed = reference_date + timedelta(
            days=days_ahead
        )

        return parsed.strftime("%Y-%m-%d")

    # ---------------------------------------------------------
    # Month names
    # ---------------------------------------------------------
    months = {
        "january": 1,
        "jan": 1,
        "february": 2,
        "feb": 2,
        "march": 3,
        "mar": 3,
        "april": 4,
        "apr": 4,
        "may": 5,
        "june": 6,
        "jun": 6,
        "july": 7,
        "jul": 7,
        "august": 8,
        "aug": 8,
        "september": 9,
        "sep": 9,
        "sept": 9,
        "october": 10,
        "oct": 10,
        "november": 11,
        "nov": 11,
        "december": 12,
        "dec": 12,
    }

    # ---------------------------------------------------------
    # Format: 18 September
    # Example: due on 18 September
    # ---------------------------------------------------------
    match = re.search(
        r'\b(\d{1,2})\s+'
        r'(january|jan|february|feb|march|mar|'
        r'april|apr|may|june|jun|july|jul|'
        r'august|aug|september|sep|sept|'
        r'october|oct|november|nov|'
        r'december|dec)\b',
        text,
        re.IGNORECASE
    )

    if match:
        day, month_word = match.groups()

        month_word = month_word.lower()

        if month_word in months:
            month = months[month_word]
            year = reference_date.year

            try:
                parsed = datetime(
                    year,
                    month,
                    int(day)
                )

                if parsed.date() < reference_date.date():
                    parsed = datetime(
                        year + 1,
                        month,
                        int(day)
                    )

                return parsed.strftime("%Y-%m-%d")

            except ValueError:
                pass

    return None


def extract_reference_number(text: str) -> Optional[str]:
    patterns = [
        r'(?:order\s?id|order\s?no)[:\s]+([a-z0-9]+)',
        r'(?:ref(?:erence)?\s?no)[:\s]+([a-z0-9]+)',
        r'(?:txn\s?id|transaction\s?id)[:\s]+([a-z0-9]+)',
    ]

    for pattern in patterns:
        match = re.search(
            pattern,
            text,
            re.IGNORECASE
        )

        if match:
            return match.group(1).upper()

    return None


def extract_entities(text: str) -> Dict[str, Any]:
    amount_data = extract_amount_and_currency(text)

    due_date = extract_date(text)

    reference_number = extract_reference_number(text)

    sender = extract_sender(text)

    return {
        "amount": amount_data["amount"],
        "currency": amount_data["currency"],
        "due_date": due_date,
        "reference_number": reference_number,
        "sender": sender,
    }