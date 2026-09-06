import json
import os


def load_sample(filename):
    path = os.path.join("sample_data", filename)

    with open(path, "r") as f:
        return json.load(f)


if __name__ == "__main__":
    gas = load_sample("gas.json")

    print(f"Loaded {len(gas)} gas records:")

    for record in gas:
        print(record)