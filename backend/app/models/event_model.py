from sqlalchemy import Column, Integer, String, Float, DateTime
from sqlalchemy.sql import func

from app.database.db import Base


class EventDB(Base):
    __tablename__ = "events"

    id = Column(Integer, primary_key=True, index=True)

    # Human-readable event identifier
    event_id = Column(String, unique=True, index=True)

    source = Column(String)
    raw_text = Column(String)
    cleaned_text = Column(String)

    event_type = Column(String, index=True)
    category = Column(String)
    responsibility = Column(String)

    amount = Column(Float, nullable=True)
    currency = Column(String, nullable=True)
    due_date = Column(String, nullable=True)
    sender = Column(String, nullable=True, index=True)
    reference_number = Column(String, nullable=True)

    ownership_state = Column(String)
    confidence = Column(Float)

    fingerprint = Column(String, index=True)

    created_at = Column(
        DateTime(timezone=True),
        server_default=func.now()
    )