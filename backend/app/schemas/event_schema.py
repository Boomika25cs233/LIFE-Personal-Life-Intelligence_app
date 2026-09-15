from typing import Optional, List, Dict, Any
from pydantic import BaseModel, Field


class EventDetectRequest(BaseModel):
    source: str
    text: str


class EventDetectResponse(BaseModel):
    event_id: Optional[str] = None
    event_type: str
    category: str
    responsibility: str
    ownership: str
    confidence: float
    entities: Dict[str, Any] = Field(default_factory=dict)
    evidence: List[str] = Field(default_factory=list)
    explanation: str = ""
    is_duplicate: bool = False
    source: Optional[str] = None