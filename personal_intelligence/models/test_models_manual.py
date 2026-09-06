from datetime import date
from models import Event, Baseline

e = Event(
    event_type="electricity_bill",
    amount=2850,
    date=date(2026, 9, 1),
    category="finance",
    responsibility="bill_payment",
    ownership_confidence=0.92
)

print(e)

b = Baseline(
    responsibility="electricity_bill",
    average=1275,
    median=1275,
    minimum=1200,
    maximum=1350,
    std_dev=55.9,
    normal_range_low=1163,
    normal_range_high=1387
)

print(b)