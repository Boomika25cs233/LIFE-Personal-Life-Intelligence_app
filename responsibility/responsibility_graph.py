from state_engine import ResponsibilityState


class ResponsibilityGraph:

    def __init__(self):
        self.relationships = {}

    def add_relationship(self, responsibility, related_responsibility):
        if responsibility not in self.relationships:
            self.relationships[responsibility] = []

        self.relationships[responsibility].append(related_responsibility)

    def get_related(self, responsibility):
        return self.relationships.get(responsibility, [])

    def get_next(self, responsibility):
        related = self.get_related(responsibility)

        if related:
            return related[0]

        return None

    def get_chain(self, responsibility):
        chain = [responsibility]

        current = responsibility

        while self.get_next(current):
            current = self.get_next(current)
            chain.append(current)

        return chain

    def predict_next(self, responsibility):

        # Predict next responsibility only after completion
        if responsibility.state == ResponsibilityState.COMPLETED:
            return self.get_next(responsibility)

        return None


if __name__ == "__main__":

    graph = ResponsibilityGraph()

    # Create simple responsibilities
    class SimpleResponsibility:

        def __init__(self, title, state):
            self.title = title
            self.state = state

        def __hash__(self):
            return hash(self.title)

        def __eq__(self, other):
            return self.title == other.title


    vehicle = SimpleResponsibility(
        "Vehicle Purchase",
        ResponsibilityState.COMPLETED
    )

    insurance = SimpleResponsibility(
        "Insurance",
        ResponsibilityState.UPCOMING
    )

    service = SimpleResponsibility(
        "Service",
        ResponsibilityState.UPCOMING
    )

    renewal = SimpleResponsibility(
        "Renewal",
        ResponsibilityState.UPCOMING
    )

    # Add relationships
    graph.add_relationship(vehicle, insurance)
    graph.add_relationship(vehicle, service)
    graph.add_relationship(insurance, renewal)

    # Test related responsibilities
    print(
        "Related to Vehicle Purchase:",
        [r.title for r in graph.get_related(vehicle)]
    )

    print(
        "Related to Insurance:",
        [r.title for r in graph.get_related(insurance)]
    )

    # Test next responsibility
    next_responsibility = graph.get_next(vehicle)

    print(
        "Next after Vehicle Purchase:",
        next_responsibility.title if next_responsibility else None
    )

    # Test chain
    chain = graph.get_chain(vehicle)

    print(
        "Chain from Vehicle Purchase:",
        [r.title for r in chain]
    )

    # Test state-aware prediction
    predicted = graph.predict_next(vehicle)

    print(
        "Predicted next:",
        predicted.title if predicted else None
    )

    # Test prediction when NOT completed
    service.state = ResponsibilityState.UPCOMING

    print(
        "Prediction for unfinished Service:",
        graph.predict_next(service)
    )