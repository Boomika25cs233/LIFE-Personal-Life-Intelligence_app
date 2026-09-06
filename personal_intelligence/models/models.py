"""
Josi Data Models
All shared data structures used across the Josi Personal Intelligence module.
"""

from dataclasses import dataclass, field
from typing import Optional, List
from datetime import date


@dataclass
class Event:
    """A single incoming event from Risha (Event Detection)."""
    event_type: str
    amount: Optional[float]
    date: date
    category: str
    responsibility: str
    ownership_confidence: float


@dataclass
class Responsibility:
    """State information from Dharshini (Responsibility State Engine)."""
    responsibility: str
    state: str
    due_date: Optional[date]
    importance: float
    urgency: float


@dataclass
class HistoricalActivity:
    """One historical data point for a responsibility."""
    responsibility: str
    date: date
    amount: Optional[float] = None


@dataclass
class Pattern:
    """Result of recurring pattern detection."""
    responsibility: str
    average_interval_days: Optional[float]
    is_recurring: bool
    confidence: str
    intervals: List[int] = field(default_factory=list)


@dataclass
class Baseline:
    """Personal 'normal' range for a responsibility's amount."""
    responsibility: str
    average: float
    median: float
    minimum: float
    maximum: float
    std_dev: float
    normal_range_low: float
    normal_range_high: float


@dataclass
class Anomaly:
    """Result of anomaly detection."""
    responsibility: str
    is_anomaly: bool
    anomaly_score: float
    reason: str


@dataclass
class Prediction:
    """Predicted next occurrence."""
    responsibility: str
    expected_next_date: Optional[date]
    confidence: str


@dataclass
class Context:
    """Contextual information."""
    responsibility: str
    today: date
    predicted_date: Optional[date]
    days_until_predicted: Optional[int]
    urgency: float
    importance: float
    is_becoming_relevant: bool


@dataclass
class Feedback:
    """User feedback on a Josi suggestion."""
    responsibility: str
    feedback_type: str
    date: date


@dataclass
class RelevanceScore:
    """Final relevance scoring output."""
    responsibility: str
    score: float
    level: str


@dataclass
class NotificationDecision:
    """Final notification action decision."""
    responsibility: str
    action: str
    reason: str


@dataclass
class Insight:
    """Human-readable message for Boomika's UI."""
    responsibility: str
    message: str