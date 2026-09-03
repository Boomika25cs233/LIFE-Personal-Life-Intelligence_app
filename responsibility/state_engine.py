from enum import Enum


class ResponsibilityState(Enum):
    DETECTED = "detected"
    VERIFIED = "verified"
    UPCOMING = "upcoming"
    DUE = "due"
    WAITING = "waiting"
    OVERDUE = "overdue"
    FOLLOW_UP = "follow_up"
    COMPLETED = "completed"
    RECURRING = "recurring"
    EXPIRED = "expired"
    CANCELLED = "cancelled"


class ResponsibilityStateEngine:

    def verify(self, responsibility):
        return self.transition(responsibility, ResponsibilityState.VERIFIED)

    def mark_upcoming(self, responsibility):
        return self.transition(responsibility, ResponsibilityState.UPCOMING)

    def mark_due(self, responsibility):
        return self.transition(responsibility, ResponsibilityState.DUE)

    def mark_waiting(self, responsibility):
        return self.transition(responsibility, ResponsibilityState.WAITING)

    def mark_overdue(self, responsibility):
        return self.transition(responsibility, ResponsibilityState.OVERDUE)

    def follow_up(self, responsibility):
        return self.transition(responsibility, ResponsibilityState.FOLLOW_UP)

    def complete(self, responsibility):
        return self.transition(responsibility, ResponsibilityState.COMPLETED)

    def cancel(self, responsibility):
        return self.transition(responsibility, ResponsibilityState.CANCELLED)

    ALLOWED_TRANSITIONS = {
        ResponsibilityState.DETECTED: [
            ResponsibilityState.VERIFIED,
            ResponsibilityState.CANCELLED
        ],
        ResponsibilityState.VERIFIED: [
            ResponsibilityState.UPCOMING,
            ResponsibilityState.CANCELLED
        ],
        ResponsibilityState.UPCOMING: [
            ResponsibilityState.DUE,
            ResponsibilityState.CANCELLED
        ],
        ResponsibilityState.DUE: [
            ResponsibilityState.WAITING,
            ResponsibilityState.OVERDUE,
            ResponsibilityState.COMPLETED,
            ResponsibilityState.CANCELLED
        ],
        ResponsibilityState.WAITING: [
            ResponsibilityState.OVERDUE,
            ResponsibilityState.COMPLETED,
            ResponsibilityState.CANCELLED
        ],
        ResponsibilityState.OVERDUE: [
            ResponsibilityState.FOLLOW_UP,
            ResponsibilityState.COMPLETED,
            ResponsibilityState.CANCELLED
        ],
        ResponsibilityState.FOLLOW_UP: [
            ResponsibilityState.COMPLETED,
            ResponsibilityState.CANCELLED
        ],
    }

    def transition(self, responsibility, new_state):
        current_state = responsibility.state

        if new_state in self.ALLOWED_TRANSITIONS.get(current_state, []):
            responsibility.state = new_state
            return True

        return False        