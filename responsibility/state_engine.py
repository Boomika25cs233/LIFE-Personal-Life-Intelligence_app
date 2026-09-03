from enum import Enum


class ResponsibilityState(Enum):
    DETECTED = "detected"
    VERIFIED = "verified"
    UPCOMING = "upcoming"
    DUE = "due"
    WAITING = "waiting"
    OVERDUE = "overdue"
    COMPLETED = "completed"
    RECURRING = "recurring"
    EXPIRED = "expired"
    CANCELLED = "cancelled"


class ResponsibilityStateEngine:

    def verify(self, responsibility):
        responsibility.state = ResponsibilityState.VERIFIED

    def mark_upcoming(self, responsibility):
        responsibility.state = ResponsibilityState.UPCOMING

    def mark_due(self, responsibility):
        responsibility.state = ResponsibilityState.DUE

    def mark_waiting(self, responsibility):
        responsibility.state = ResponsibilityState.WAITING

    def mark_overdue(self, responsibility):
        responsibility.state = ResponsibilityState.OVERDUE

    def complete(self, responsibility):
        responsibility.state = ResponsibilityState.COMPLETED

    def cancel(self, responsibility):
        responsibility.state = ResponsibilityState.CANCELLED