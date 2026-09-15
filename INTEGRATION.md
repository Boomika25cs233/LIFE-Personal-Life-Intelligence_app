# LIFE Event Intelligence — Integration Contract

## 1. Purpose

This document defines the integration contract between the LIFE Event Intelligence backend and the other LIFE modules.

The Event Intelligence module:

- Receives raw events such as SMS, email, notification, or manual input.
- Cleans and normalizes the text.
- Extracts useful entities.
- Detects the event type.
- Assigns a category and responsibility.
- Estimates ownership confidence.
- Generates evidence and a user-facing explanation.
- Detects duplicate events.
- Stores valid events in the database.
- Exposes processed information through REST APIs.

---

## 2. Base URL

Local backend:

`http://127.0.0.1:8001`

Swagger:

`http://127.0.0.1:8001/docs`

---

## 3. Health Check

### Endpoint

`GET /health`

### Response

```json
{
  "status": "healthy"
}
## 4. Event Detection

### Endpoint

`POST /api/v1/events/detect`

### Request

```json
{
  "source": "SMS",
  "text": "Electricity Board: Your bill of ₹2763 is due on 18 September."
}
## 5. Event Detection Response

A successful detection returns:

```json
{
  "event_id": "EVT-009",
  "event_type": "ELECTRICITY_BILL",
  "category": "FINANCE",
  "responsibility": "BILL_PAYMENT",
  "ownership": "POSSIBLE",
  "confidence": 0.35,
  "entities": {
    "amount": 2763.0,
    "currency": "INR",
    "due_date": "2026-09-18",
    "reference_number": null,
    "sender": "Electricity Board"
  },
  "evidence": [
    "Same sender as a previously confirmed event",
    "This matches a known, recognizable event category"
  ],
  "explanation": "This might be yours. Here's why we think so: same sender as a previously confirmed event, and this matches a known, recognizable event category.",
  "is_duplicate": false,
  "source": "SMS"
}
## 6. Response Fields

| Field | Type | Description |
|---|---|---|
| event_id | string/null | Unique ID for a stored event |
| event_type | string | Detected event type |
| category | string | High-level category |
| responsibility | string | Required action |
| ownership | string | Ownership state |
| confidence | float | Score from 0.0 to 1.0 |
| entities | object | Extracted information |
| evidence | array | Supporting evidence |
| explanation | string | User-facing explanation |
| is_duplicate | boolean | Whether event is a duplicate |
| source | string/null | Original source |
## 7. Extracted Entities

The `entities` object contains:

- `amount`
- `currency`
- `due_date`
- `reference_number`
- `sender`

Example:

```json
{
  "amount": 2763.0,
  "currency": "INR",
  "due_date": "2026-09-18",
  "reference_number": null,
  "sender": "Electricity Board"
}
## 8. Event Taxonomy

### FINANCE

- ELECTRICITY_BILL
- CREDIT_CARD_BILL
- EMI_PAYMENT
- RENT_PAYMENT
- SUBSCRIPTION_PAYMENT
- GENERIC_PAYMENT

### MEDICAL

- MEDICINE_REFILL
- DOCTOR_APPOINTMENT
- MEDICAL_REPORT

### EDUCATION

- EXAM
- ASSIGNMENT
- FEE_PAYMENT

### HOUSEHOLD

- GAS_PURCHASE
- MILK_DELIVERY
- GROCERY

### TRANSPORT

- FUEL
- VEHICLE_SERVICE
- VEHICLE_INSURANCE

### SHOPPING

- ORDER_PLACED
- ORDER_SHIPPED
- ORDER_DELIVERED
- REFUND_INITIATED
- REFUND_RECEIVED

### WAITING

- WAITING_REFUND
- WAITING_REPLACEMENT
- WAITING_CERTIFICATE
- WAITING_TECHNICIAN
- WAITING_REPAYMENT

### WARRANTY

- WARRANTY_REGISTRATION
- WARRANTY_EXPIRY

### UNKNOWN

- UNKNOWN_EVENT
## 9. Ownership States

### UNKNOWN

Not enough evidence is available to determine whether the event belongs to the user.

### POSSIBLE

Some evidence exists, but ownership is not strongly established.

### LIKELY

There is stronger evidence that the event belongs to the user.

### CONFIRMED

Available evidence is sufficient to treat the event as confirmed.

### LEARNED

The system has learned a sender association through repeated user confirmations and can use that information for future events.
## 10. Confidence

Confidence is represented between `0.0` and `1.0`.

Example:

`0.35 = 35%`

### Evidence weights

| Evidence | Weight |
|---|---:|
| Previously confirmed sender | 0.30 |
| Previously confirmed event type | 0.30 |
| Account identifier match | 0.15 |
| Recurring similar evidence | 0.20 |
| Known event category | 0.05 |

### Confidence states

| Score | State |
|---|---|
| 0.81 – 1.00 | CONFIRMED |
| 0.61 – 0.80 | LIKELY |
| 0.31 – 0.60 | POSSIBLE |
| 0.00 – 0.30 | UNKNOWN |

## 11. Feedback

### Endpoint

`POST /api/v1/events/feedback`

### Request

```json
{
  "event_id": "EVT-009",
  "feedback_type": "CONFIRM"
}

## 12. Retrieve One Event

### Endpoint

`GET /api/v1/events/{event_id}`

Example:

`GET /api/v1/events/EVT-009`

Returns the stored event record.

## 13. Retrieve Event History

### Endpoint

`GET /api/v1/events`

Returns stored events ordered from newest to oldest.

## 14. Event Filtering

### Category

`GET /api/v1/events?category=FINANCE`

### Event type

`GET /api/v1/events?event_type=ELECTRICITY_BILL`

### Ownership

`GET /api/v1/events?ownership=POSSIBLE`

### Limit

`GET /api/v1/events?limit=10`

Filters can be combined.

Example:

`GET /api/v1/events?category=FINANCE&ownership=POSSIBLE`

## 15. EventRecord Contract

Historical event responses contain:

- `event_id`
- `source`
- `event_type`
- `category`
- `responsibility`
- `amount`
- `currency`
- `due_date`
- `sender`
- `reference_number`
- `ownership_state`
- `confidence`
- `created_at`

These fields can be used by downstream modules for reminders, history, filtering, dashboards, explanations, and action prioritization.

## 16. Duplicate Detection

The backend creates a fingerprint for relevant event/entity information.

If the same event is received again:

```json
{
  "event_id": null,
  "is_duplicate": true
}

## 17. Downstream Module Usage

### Dharshini

Use:

`POST /api/v1/events/detect`

to submit incoming events.

Use:

`GET /api/v1/events/{event_id}`

to retrieve a specific event.

Use:

`GET /api/v1/events?ownership=POSSIBLE`

to find events requiring attention.

### Josi

Use:

`GET /api/v1/events`

to retrieve historical events.

Filter using:

- `category`
- `event_type`
- `ownership`

### Boomika

Use these response fields for display:

- `event_type`
- `category`
- `responsibility`
- `ownership`
- `confidence`
- `entities`
- `evidence`
- `explanation`

Confidence can be displayed as:

`confidence * 100`

Example:

`0.35 → 35%`

The `explanation` field is already user-friendly.

## 18. End-to-End Flow

```text
Raw Event
    ↓
Preprocessing
    ↓
Entity Extraction
    ↓
Event Detection
    ↓
Taxonomy
    ↓
Ownership + Confidence
    ↓
Evidence + Explanation
    ↓
Duplicate Detection
    ↓
Database
    ↓
REST API
    ↓
Dharshini / Josi / Boomika

## 19. Error Handling

### 404 — Event not found

```json
{
  "detail": "Event not found: EVT-999"
}

## 20. Non-Goals

The Event Intelligence backend does not currently:

- Send notifications.
- Schedule reminders.
- Execute payments.
- Contact external organizations.
- Automatically perform user actions.
- Guarantee ownership without sufficient evidence.
- Replace downstream reminder or action modules.

## 21. Current Limitations

- Entity extraction is rule-based.
- Event detection is keyword-based.
- Ownership learning depends on feedback history.
- Confidence depends on implemented evidence sources.
- Unknown events may require manual review.
- External integrations are outside the current scope.

## 22. Testing Status

Current automated test status:

**155 passed, 3 warnings**

Integration tests cover:

### Dharshini

- Event detection and retrieval
- Ownership filtering

### Josi

- Historical event retrieval
- Category filtering

### Boomika

- Display-ready explanation
- Confidence percentage conversion

The full test suite currently has **zero failures**.

## 23. Primary API Endpoints

```text
GET  /health
POST /api/v1/events/detect
POST /api/v1/events/feedback
GET  /api/v1/events/{event_id}
GET  /api/v1/events

This document is the current integration contract for the LIFE Event Intelligence backend.

Additional ambiguity limitation:
- The current event detector assumes one primary event per message.
- Generic phrases such as "policy renewal" may be interpreted according to the current controlled taxonomy. For example, "policy renewal" is currently mapped to VEHICLE_INSURANCE.
- This is an accepted limitation of the current keyword-based detector. More context-aware classification can be introduced in a future version.

PHASE 28 — NLP / LLM DESIGN DECISION

The current LIFE Event Intelligence backend intentionally uses a
local rule-based classification pipeline rather than a mandatory
LLM or external NLP API.

Reasons:
- Privacy: messages may contain financial, medical, and personal information.
- Explainability: every classification can be traced to explicit evidence and rules.
- Reliability: the core pipeline does not depend on network connectivity or an external API.
- Cost: there are no per-request LLM/API inference costs.
- Latency: local rule-based processing avoids network/API latency.
- Offline capability: the core event detection continues to work without internet access.

Known limitation:
- Rule-based matching may not understand completely novel phrasing,
  synonyms, or semantic relationships that are not represented in the taxonomy.

Future enhancement options:
- Fuzzy string matching for minor spelling variations.
- Offline sentence embeddings for semantic similarity.
- An optional LLM fallback for messages that cannot be classified locally.

Any future advanced NLP component should remain optional and must not
make the core event detection pipeline dependent on an external service.