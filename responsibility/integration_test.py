from datetime import date

from responsibility import Responsibility
from state_engine import ResponsibilityState
from responsibility_graph import ResponsibilityGraph


# Create graph
graph = ResponsibilityGraph()


# -----------------------------
# 1. Create responsibilities
# -----------------------------

bill = Responsibility(
    "Electricity Bill",
    "bill",
    date(2026, 9, 1)
)

payment = Responsibility(
    "Bill Payment",
    "payment"
)


# -----------------------------
# 2. State transitions
# -----------------------------

bill.state = ResponsibilityState.VERIFIED
print("1. Verified:", bill)

bill.state = ResponsibilityState.UPCOMING
print("2. Upcoming:", bill)

bill.state = ResponsibilityState.DUE
print("3. Due:", bill)


# -----------------------------
# 3. Overdue detection
# -----------------------------

engine_due_date = date(2026, 9, 1)

if date.today() > engine_due_date:
    bill.state = ResponsibilityState.OVERDUE

print("4. Overdue:", bill)


# -----------------------------
# 4. Waiting system
# -----------------------------

bill.state = ResponsibilityState.DUE

bill.waiting_for = "Payment confirmation"
bill.state = ResponsibilityState.WAITING

print("5. Waiting:", bill)
print("   Waiting for:", bill.waiting_for)


# -----------------------------
# 5. Evidence-based completion
# -----------------------------

bill.state = ResponsibilityState.COMPLETED

print("6. Completed:", bill)


# -----------------------------
# 6. Relationship graph
# -----------------------------

graph.add_relationship(bill, payment)

print(
    "7. Related:",
    [r.title for r in graph.get_related(bill)]
)


# -----------------------------
# 7. Prediction
# -----------------------------

next_responsibility = graph.predict_next(bill)

if next_responsibility:
    print(
        "8. Predicted next:",
        next_responsibility.title
    )
else:
    print("8. Predicted next: None")