from responsibility import Responsibility
from state_engine import ResponsibilityStateEngine


bill = Responsibility(
    title="Electricity Bill",
    responsibility_type="Bill"
)

engine = ResponsibilityStateEngine()

print("Initial:", bill)

engine.verify(bill)
print("After verification:", bill)

engine.mark_upcoming(bill)
print("Upcoming:", bill)

engine.mark_due(bill)
print("Due:", bill)

engine.mark_overdue(bill)
print("Overdue:", bill)

engine.complete(bill)
print("Completed:", bill)