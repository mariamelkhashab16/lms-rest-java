import psycopg2
import csv
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

# SQL query to find books not borrowed in the last 6 months
SQL_QUERY = """
    select b.*
    from book b
    left join borrowing_history bh on b.id = bh.book_id
    and bh.borrow_date >= CURRENT_DATE - INTERVAL '6 months'
    group by b.id
    having COUNT(bh.id) = 0
            """

def fetch_books():
    # Connect to the database
    try:
        conn = psycopg2.connect(**DB_CONFIG)
        cursor = conn.cursor()
        cursor.execute(SQL_QUERY)
        books = cursor.fetchall()

        return books

    except Exception as e:
        print(f"Error fetching books: {e}")
        return []

    finally:
        if conn:
            conn.close()

def export_books_to_csv(books, filename='books_not_borrowed.csv'):
    if not books:
        print("No books found to export.")
        return
    
    try:
        with open(filename, 'w', newline='') as csvfile:
            fieldnames = ['id', 'author', 'copies_available', 'isbn', 'title']
            writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
            writer.writeheader()
            for book in books:
                writer.writerow({
                    'id': book[0],
                    'author': book[1],
                    'copies_available': book[2],
                    'isbn': book[3],
                    'title': book[4],
                })

        print(f"Exported {len(books)} books to {filename}")
    except Exception as e:
        print(f"Error exporting to CSV: {e}")

if __name__ == "__main__":
    
    books = fetch_books()
    export_books_to_csv(books)
