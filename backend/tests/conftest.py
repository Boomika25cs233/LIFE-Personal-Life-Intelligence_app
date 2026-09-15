import pytest

from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.pool import StaticPool

from fastapi.testclient import TestClient

from app.database.db import Base, get_db

from app.models.event_model import EventDB
from app.models.sender_model import SenderDB
from app.models.feedback_model import FeedbackDB

from app.main import app


TEST_DATABASE_URL = "sqlite://"


@pytest.fixture
def db_session():
    engine = create_engine(
        TEST_DATABASE_URL,
        connect_args={"check_same_thread": False},
        poolclass=StaticPool,
    )

    Base.metadata.create_all(bind=engine)

    TestingSessionLocal = sessionmaker(
        bind=engine
    )

    session = TestingSessionLocal()

    yield session

    session.close()


@pytest.fixture
def client_factory():
    """
    Returns a function that creates a FastAPI TestClient
    using the provided test database session.
    """

    def _make_client(db_session):

        def override_get_db():
            yield db_session

        app.dependency_overrides[get_db] = override_get_db

        return TestClient(app)

    return _make_client