# LIFE — Intelligent Personal Responsibility Assistant

LIFE turns everyday notifications, SMS messages, and receipts into structured, trackable responsibilities.

This repository contains the **Event Intelligence module** developed for LIFE.

## What This Module Does

The Event Intelligence pipeline processes incoming text through several stages:

```text
RAW TEXT
   ↓
Preprocessing
   ↓
Entity Extraction
   ↓
Event Detection
   ↓
Classification
   ↓
Ownership Inference
   ↓
Confidence Scoring
   ↓
Evidence & Explanation
   ↓
STRUCTURED LIFE EVENT
   ↓
SQLite Database

## Key Features

- Raw SMS and notification text processing
- Entity extraction
- Event type detection
- Category classification
- Responsibility classification
- Ownership inference
- Confidence scoring
- Evidence generation
- Human-readable explanations
- Duplicate event detection
- Sender learning through feedback
- Offline-first processing
- Local SQLite storage
- Automated regression testing

## Technology Stack

- Python
- FastAPI
- Pydantic
- SQLAlchemy
- SQLite
- Pytest
- Uvicorn
- Rule-based NLP

## Key Features

- Raw SMS and notification text processing
- Entity extraction
- Event type detection
...
- Automated regression testing

## Technology Stack

- Python
- FastAPI
- Pydantic
- SQLAlchemy
- SQLite
- Pytest
- Uvicorn
- Rule-based NLP

## API Endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/health` | Health check |
| POST | `/api/v1/events/detect` | Detect and create an event |
| POST | `/api/v1/events/feedback` | Submit event feedback |
| GET | `/api/v1/events/{event_id}` | Retrieve one event |
| GET | `/api/v1/events` | List events |

## Running the Backend

From the project root:

```powershell
cd C:\Users\risha\OneDrive\Documents\LIFE\backend

## Architecture

```text
Incoming Message
       |
       v
Preprocessing
       |
       v
Entity Extraction
       |
       v
Event Detection
       |
       v
Category + Responsibility
       |
       v
Ownership Evidence
       |
       v
Confidence Scoring
       |
       v
Evidence + Explanation
       |
       v
Duplicate Detection
       |
       v
Structured Event
       |
       v
SQLite Database

## Design Principles

The Event Intelligence module is designed around:

- **Privacy**
- **Explainability**
- **Offline capability**
- **Deterministic behavior**
- **Low latency**
- **Low operational cost**
- **Controlled event taxonomy**
- **Graceful handling of unknown events**

Detailed technical decisions are documented in `DESIGN_DECISIONS.md`.

## NLP / LLM Decision

The current implementation intentionally uses a local rule-based pipeline instead of depending on an external LLM.

This provides:

- Better privacy for personal messages
- Offline operation
- Predictable behavior
- Explainable classifications
- No per-request API cost
- No network dependency

An advanced NLP or LLM component can be added later as an **optional fallback**.

The core event detection pipeline must remain functional without an external service.

## Ownership and Confidence

Ownership is inferred using explicit evidence from the local database.

Examples of evidence include:

- Previously confirmed sender
- Known event category

Confidence is calculated using configurable evidence weights.

Duplicate evidence flags are ignored so that the same evidence cannot artificially increase confidence.

A sender can become **LEARNED** after reaching the configured confirmation threshold.

## Duplicate Detection

The system creates an event fingerprint using relevant event information.

The fingerprint is used to identify whether a newly processed message represents a duplicate event.

This helps prevent the same responsibility from being recorded multiple times.

## Event Taxonomy

The system uses a controlled event taxonomy containing categories such as:

- EMI payments
- Credit card bills
- Rent payments
- Electricity bills
- Subscription payments
- Doctor appointments
- Medicine refills
- Medical reports
- Exams
- Assignments
- Vehicle insurance
- Vehicle service
- Fuel
- Warranty expiry
- Refunds
- Orders
- Gas purchases
- Grocery
- Milk delivery

If no known event pattern matches the message, the system safely returns:

```text
UNKNOWN_EVENT
