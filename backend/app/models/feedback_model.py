from sqlalchemy import Column, Integer, String, DateTime, ForeignKey
from sqlalchemy.sql import func

from app.database.db import Base


class FeedbackDB(Base):
    __tablename__ = "feedback"

    id = Column(Integer, primary_key=True, index=True)

    event_id = Column(
        String,
        ForeignKey("events.event_id"),
        index=True
    )

    # CONFIRM | REJECT | IGNORE | COMPLETE
    feedback_type = Column(String)

    created_at = Column(
        DateTime(timezone=True),
        server_default=func.now()
    )