from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker, declarative_base


# SQLite database file will be created at backend/life.db
DATABASE_URL = "sqlite:///./life.db"


# SQLite can be accessed safely by FastAPI across different threads
engine = create_engine(
    DATABASE_URL,
    connect_args={"check_same_thread": False}
)


# Factory for creating database sessions
SessionLocal = sessionmaker(
    autocommit=False,
    autoflush=False,
    bind=engine
)


# Base class for all database models
Base = declarative_base()


def get_db():
    """
    Provides a database session for a single request
    and guarantees that it is closed afterward.
    """

    db = SessionLocal()

    try:
        yield db
    finally:
        db.close()