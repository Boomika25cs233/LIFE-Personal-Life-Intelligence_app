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


if __name__ == "__main__":
    graph = ResponsibilityGraph()

    graph.add_relationship("Vehicle Purchase", "Insurance")
    graph.add_relationship("Vehicle Purchase", "Service")
    graph.add_relationship("Insurance", "Renewal")

    print("Related to Vehicle Purchase:",
          graph.get_related("Vehicle Purchase"))

    print("Related to Insurance:",
          graph.get_related("Insurance"))

    print("Next after Vehicle Purchase:",
          graph.get_next("Vehicle Purchase"))

    print("Next after Service:",
          graph.get_next("Service"))

    print("Chain from Vehicle Purchase:",
      graph.get_chain("Vehicle Purchase"))