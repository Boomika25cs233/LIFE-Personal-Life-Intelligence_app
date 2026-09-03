from datetime import date
from state_engine import ResponsibilityState


class Responsibility:
    def __init__(self, title, responsibility_type, due_date=None, owner=None):
        self.title = title
        self.responsibility_type = responsibility_type
        self.due_date = due_date
        self.owner = owner
        self.state = ResponsibilityState.DETECTED

    def __str__(self):
        return f"{self.title} - {self.state.value}"