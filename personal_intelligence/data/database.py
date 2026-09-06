"""
Josi Database Layer (SQLite)
Handles all persistent storage: historical activities, patterns,
predictions, anomalies, feedback, and notification history.
"""

import sqlite3
import os
from datetime import date
from typing import List, Optional

DB_PATH = os.path.join("personal_intelligence", "data", "josi.db")


def get_connection():
    conn = sqlite3.connect(DB_PATH)
    conn.row_factory = sqlite3.Row
    return conn


def init_db():
    """Create all tables if they don't already exist."""
    os.makedirs(os.path.dirname(DB_PATH), exist_ok=True)
    conn = get_connection()
    cursor = conn.cursor()

    cursor.execute("""
        CREATE TABLE IF NOT EXISTS historical_activities (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            responsibility TEXT NOT NULL,
            date TEXT NOT NULL,
            amount REAL
        )
    """)

    cursor.execute("""
        CREATE TABLE IF NOT EXISTS patterns (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            responsibility TEXT NOT NULL,
            average_interval_days REAL,
            is_recurring INTEGER,
            confidence TEXT,
            calculated_at TEXT
        )
    """)

    cursor.execute("""
        CREATE TABLE IF NOT EXISTS predictions (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            responsibility TEXT NOT NULL,
            expected_next_date TEXT,
            confidence TEXT,
            calculated_at TEXT
        )
    """)

    cursor.execute("""
        CREATE TABLE IF NOT EXISTS anomalies (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            responsibility TEXT NOT NULL,
            is_anomaly INTEGER,
            anomaly_score REAL,
            reason TEXT,
            detected_at TEXT
        )
    """)

    cursor.execute("""
        CREATE TABLE IF NOT EXISTS feedback (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            responsibility TEXT NOT NULL,
            feedback_type TEXT NOT NULL,
            date TEXT NOT NULL
        )
    """)

    cursor.execute("""
        CREATE TABLE IF NOT EXISTS notification_history (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            responsibility TEXT NOT NULL,
            action TEXT NOT NULL,
            reason TEXT,
            notified_at TEXT
        )
    """)

    conn.commit()
    conn.close()


# ---------- Historical Activities ----------

def insert_activity(responsibility: str, activity_date: date, amount: Optional[float]):
    conn = get_connection()
    conn.execute(
        "INSERT INTO historical_activities (responsibility, date, amount) VALUES (?, ?, ?)",
        (responsibility, activity_date.isoformat(), amount)
    )
    conn.commit()
    conn.close()


def get_activities(responsibility: str) -> List[sqlite3.Row]:
    conn = get_connection()
    rows = conn.execute(
        "SELECT * FROM historical_activities WHERE responsibility = ? ORDER BY date ASC",
        (responsibility,)
    ).fetchall()
    conn.close()
    return rows


# ---------- Feedback ----------

def insert_feedback(responsibility: str, feedback_type: str, feedback_date: date):
    conn = get_connection()
    conn.execute(
        "INSERT INTO feedback (responsibility, feedback_type, date) VALUES (?, ?, ?)",
        (responsibility, feedback_type, feedback_date.isoformat())
    )
    conn.commit()
    conn.close()


def get_feedback_history(responsibility: str) -> List[sqlite3.Row]:
    conn = get_connection()
    rows = conn.execute(
        "SELECT * FROM feedback WHERE responsibility = ? ORDER BY date DESC",
        (responsibility,)
    ).fetchall()
    conn.close()
    return rows


# ---------- Notification History ----------

def insert_notification(responsibility: str, action: str, reason: str, notified_at: date):
    conn = get_connection()
    conn.execute(
        "INSERT INTO notification_history (responsibility, action, reason, notified_at) VALUES (?, ?, ?, ?)",
        (responsibility, action, reason, notified_at.isoformat())
    )
    conn.commit()
    conn.close()


def delete_activity(activity_id: int):
    conn = get_connection()
    conn.execute("DELETE FROM historical_activities WHERE id = ?", (activity_id,))
    conn.commit()
    conn.close()


if __name__ == "__main__":
    init_db()
    print("Database initialized.")

    # Insert
    insert_activity("electricity_bill", date(2026, 8, 1), 1250)
    print("Inserted 1 activity.")

    # Read
    rows = get_activities("electricity_bill")
    print(f"Found {len(rows)} activities:")
    for r in rows:
        print(f"  id={r['id']}, date={r['date']}, amount={r['amount']}")

    # Feedback
    insert_feedback("electricity_bill", "CONFIRM", date(2026, 8, 1))
    fb = get_feedback_history("electricity_bill")
    print(f"Feedback entries: {len(fb)}")

    # Update via delete + reinsert (simple approach for beginner stack)
    if rows:
        delete_activity(rows[-1]["id"])
        print("Deleted last inserted activity (demonstrates delete).")

    rows_after = get_activities("electricity_bill")
    print(f"Activities remaining: {len(rows_after)}")