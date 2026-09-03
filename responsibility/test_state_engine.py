from responsibility import Responsibility
from state_engine import ResponsibilityStateEngine, ResponsibilityState


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

print("\nTesting transition rules:")

bill.state = ResponsibilityState.DETECTED

print("DETECTED → VERIFIED:",
      engine.transition(bill, ResponsibilityState.VERIFIED))

print("VERIFIED → COMPLETED:",
      engine.transition(bill, ResponsibilityState.COMPLETED))

print("Current state:", bill)

print("\nTesting DUE → WAITING:")

bill.state = ResponsibilityState.DUE

print("DUE → WAITING:",
      engine.transition(bill, ResponsibilityState.WAITING))

print("Current state:", bill)

print("\nTesting OVERDUE → FOLLOW_UP:")

bill.state = ResponsibilityState.OVERDUE

print("OVERDUE → FOLLOW_UP:",
      engine.transition(bill, ResponsibilityState.FOLLOW_UP))

print("Current state:", bill)

print("\nTesting FOLLOW_UP → COMPLETED:")

bill.state = ResponsibilityState.FOLLOW_UP

print("FOLLOW_UP → COMPLETED:",
      engine.transition(bill, ResponsibilityState.COMPLETED))

print("Current state:", bill)