from pydantic import BaseModel
from typing import Optional
from datetime import datetime


class EventRecord(BaseModel):
    """
    Full representation of a stored event.

    Used by event retrieval endpoints.
    This is separate from EventDetectResponse because
    evidence and explanation are computed during detection
    and are not stored in the database.
    """

    event_id: str
    source: Optional[str] = None
    event_type: str
    category: str
    responsibility: str
    amount: Optional[float] = None
    currency: Optional[str] = None
    due_date: Optional[str] = None
    sender: Optional[str] = None
    reference_number: Optional[str] = None
    ownership_state: str
    confidence: float
    created_at: Optional[datetime] = None

    class Config:
        from_attributes = True