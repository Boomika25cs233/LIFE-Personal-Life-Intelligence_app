from sqlalchemy import Column, Integer, String, Boolean, DateTime
from sqlalchemy.sql import func

from app.database.db import Base


class SenderDB(Base):
    __tablename__ = "senders"

    id = Column(Integer, primary_key=True, index=True)

    # Example: "Electricity Board"
    name = Column(String, unique=True, index=True)

    # Whether the user has confirmed this sender
    is_confirmed = Column(Boolean, default=False)

    # Number of times the user confirmed events from this sender
    confirmation_count = Column(Integer, default=0)

    created_at = Column(
        DateTime(timezone=True),
        server_default=func.now()
    )