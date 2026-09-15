from pydantic import BaseModel


class FeedbackRequest(BaseModel):
    event_id: str
    feedback_type: str  # CONFIRM | REJECT | IGNORE | COMPLETE


class FeedbackResponse(BaseModel):
    success: bool
    event_id: str = ""
    feedback_type: str = ""
    error: str = ""