import json
import os

import pytest

from app.services.pipeline import run_pipeline


# Locate sample_data/messages.json relative to the project root
SAMPLE_DATA_PATH = os.path.join(
    os.path.dirname(__file__),
    "..",
    "..",
    "sample_data",
    "messages.json",
)


with open(SAMPLE_DATA_PATH, encoding="utf-8") as f:
    ALL_SAMPLES = json.load(f)


# Duplicate behavior is tested separately.
EVENT_TYPE_SAMPLES = [
    sample
    for sample in ALL_SAMPLES
    if "duplicate" not in sample.get("note", "")
]


@pytest.mark.parametrize(
    "sample",
    EVENT_TYPE_SAMPLES,
    ids=lambda sample: f"msg_{sample['id']}"
)
def test_sample_message_detects_expected_event(
    sample,
    db_session
):
    result = run_pipeline(
        db_session,
        sample["source"],
        sample["text"]
    )

    assert result["event_type"] == sample["expected_event_type"], (
        f"Message #{sample['id']} ('{sample['text']}') "
        f"expected {sample['expected_event_type']} "
        f"but got {result['event_type']}"
    )

    assert result["category"] == sample["expected_category"], (
        f"Message #{sample['id']} expected category "
        f"{sample['expected_category']} "
        f"but got {result['category']}"
    )