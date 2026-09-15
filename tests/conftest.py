"""
Shared pytest configuration.
Adds all personal_intelligence submodules to the path.
"""

import os
import sys

ROOT = os.path.dirname(os.path.dirname(__file__))
BASE = os.path.join(ROOT, "personal_intelligence")

for sub in [
    "models",
    "data",
    "patterns",
    "baseline",
    "anomaly",
    "prediction",
    "context",
    "scoring",
    "notification",
    "learning",
    "insights",
    ""
]:
    sys.path.insert(0, os.path.join(BASE, sub))

os.chdir(ROOT)