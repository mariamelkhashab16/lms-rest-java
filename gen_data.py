import psycopg2
from faker import Faker
import random
from datetime import datetime, timedelta
from psycopg2.extras import execute_batch
from dotenv import load_dotenv
import os

# Load environment variables from .env file
load_dotenv()
DB_URL = os.getenv('DB_URL')
DB_USERNAME = os.getenv('DB_USERNAME')
DB_PASSWORD = os.getenv('DB_PASSWORD')
DB_NAME = os.getenv('DB_NAME')
DB_HOST = os.getenv('DB_HOST')
DB_PORT = os.getenv('DB_PORT')

# Database connection configuration
DB_CONFIG = {
    'dbname': DB_NAME,
    'user': DB_USERNAME,
    'password': DB_PASSWORD,
    'host': DB_HOST,
    'port': DB_PORT
}

# Initialize Faker
faker = Faker()

# Generate random data
def generate_books(num_books):
    books = []
    for _ in range(num_books):
        book = {
            "title": faker.sentence(nb_words=3),
            "author": faker.name(),
            "isbn": faker.isbn13(),
            "copies_available": random.randint(1, 10),
        }
        books.append(book)
    return books

def generate_users(num_users):
    users = []
    for _ in range(num_users):
        user = {
            "name": faker.name(),
            "email": faker.unique.email(),
            "role": random.choice(["ADMIN", "PATRON"]),
        }
        users.append(user)
    return users

def generate_borrowing_events(users, books, num_events):
    events = []
    for _ in range(num_events):
        user = random.choice(users)
        book = random.choice(books)
        borrow_date = faker.date_between(start_date="-1y", end_date="today")
        return_date = borrow_date + timedelta(days=random.randint(1, 30))
        event = {
            "user_id": user["id"],
            "book_id": book["id"],
            "borrow_date": borrow_date,
            "return_date": return_date,
        }
        events.append(event)
    return events

def insert_data():
    try:
        conn = psycopg2.connect(**DB_CONFIG)
        cursor = conn.cursor()

        # Insert books
        books = generate_books(100)
        print("Inserting books...")
        for book in books:
            cursor.execute(
                """
                INSERT INTO book (title, author, isbn, copies_available)
                VALUES (%s, %s, %s, %s)
                RETURNING id
                """,
                (book["title"], book["author"], book["isbn"], book["copies_available"]),
            )
            book["id"] = cursor.fetchone()[0]  # Store the returned ID

        # Insert users
        users = generate_users(50)
        print("Inserting users...")
        for user in users:
            cursor.execute(
                """
                INSERT INTO "user" (name, email, role)
                VALUES (%s, %s, %s)
                RETURNING id
                """,
                (user["name"], user["email"], user["role"]),
            )
            user["id"] = cursor.fetchone()[0]  # Store the returned ID

        # Insert borrowing events
        events = generate_borrowing_events(users, books, 200)
        print("Inserting borrowing events...")
        execute_batch(
            cursor,
            """
            INSERT INTO borrowing_history (user_id, book_id, borrow_date, return_date)
            VALUES (%(user_id)s, %(book_id)s, %(borrow_date)s, %(return_date)s)
            """,
            events,
        )

        # Commit changes
        conn.commit()
        print("Sample data successfully inserted!")

    except Exception as e:
        print(f"Error inserting data: {e}")
        conn.rollback()
    finally:
        if cursor:
            cursor.close()
        if conn:
            conn.close()

if __name__ == "__main__":
    insert_data()