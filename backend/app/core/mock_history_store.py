"""
Temporary in-memory mock of user history.

This stands in for the real database until the
SQLite database is added in later phases.
"""

CONFIRMED_SENDERS = {
    "Electricity Board",
}

CONFIRMED_EVENT_TYPES = {
    "ELECTRICITY_BILL",
}
# TEMPORARY: simulates fingerprints of events already processed.
# Real version comes from a database table in Phase 18-20.
SEEN_FINGERPRINTS: list = []