"""
Historical Data Processor
Loads, cleans, filters, and sorts historical activity data.
"""

import json
import os
from datetime import date, datetime
from typing import List, Optional

import sys
sys.path.append(os.path.join(os.path.dirname(__file__), "..", "models"))
from models import HistoricalActivity


SAMPLE_DATA_DIR = "sample_data"


def load_raw_file(filename: str) -> list:
    """Load a raw JSON file. Returns [] if file is missing or invalid."""
    path = os.path.join(SAMPLE_DATA_DIR, filename)
    if not os.path.exists(path):
        print(f"WARNING: file not found: {path}")
        return []
    try:
        with open(path, "r") as f:
            return json.load(f)
    except json.JSONDecodeError:
        print(f"WARNING: invalid JSON in {path}")
        return []


def parse_date(date_str: str) -> Optional[date]:
    """Safely parse a YYYY-MM-DD string into a date object."""
    try:
        return datetime.strptime(date_str, "%Y-%m-%d").date()
    except (ValueError, TypeError):
        return None


def clean_records(raw_records: list) -> List[HistoricalActivity]:
    """
    Convert raw dicts into HistoricalActivity objects.
    Skips records with missing/invalid date. Amount is optional.
    """
    cleaned = []
    for record in raw_records:
        responsibility = record.get("responsibility")
        date_value = parse_date(record.get("date"))
        amount = record.get("amount")

        if not responsibility or date_value is None:
            # Skip clearly broken records instead of crashing
            continue

        # Amount must be a number if present
        if amount is not None:
            try:
                amount = float(amount)
            except (ValueError, TypeError):
                amount = None

        cleaned.append(HistoricalActivity(
            responsibility=responsibility,
            date=date_value,
            amount=amount
        ))
    return cleaned


def load_historical_data(filename: str, responsibility_filter: Optional[str] = None) -> List[HistoricalActivity]:
    """
    Full pipeline: load -> clean -> filter -> sort.
    """
    raw = load_raw_file(filename)
    cleaned = clean_records(raw)

    if responsibility_filter:
        cleaned = [r for r in cleaned if r.responsibility == responsibility_filter]

    cleaned.sort(key=lambda r: r.date)
    return cleaned


def calculate_date_gaps(records: List[HistoricalActivity]) -> List[int]:
    """Return list of day-gaps between consecutive sorted records."""
    gaps = []
    for i in range(1, len(records)):
        gap = (records[i].date - records[i - 1].date).days
        gaps.append(gap)
    return gaps


if __name__ == "__main__":
    data = load_historical_data("gas.json", responsibility_filter="gas_refill")
    print(f"Loaded {len(data)} clean records:")
    for r in data:
        print(r)

    gaps = calculate_date_gaps(data)
    print(f"Date gaps (days): {gaps}")