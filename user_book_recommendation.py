import pandas as pd
import psycopg2
from collections import Counter
from dotenv import load_dotenv
import os
import argparse


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

# Function to fetch borrowing history from the database
def fetch_borrow_history():
    borrow_history_data  = None
    try:
        conn = psycopg2.connect(**DB_CONFIG)
        cursor = conn.cursor()
        
        query = "SELECT user_id, book_id from borrowing_history"
        cursor.execute(query)
        
        borrow_history_data = cursor.fetchall()
        borrow_history_data_df =  pd.DataFrame(borrow_history_data, columns=['user_id', 'book_id'])
    except Exception as e:
        print(e)

    finally:
        if conn:
            conn.close()

    return borrow_history_data_df

# Function to get borrowing history for a specific user
def get_user_books(user_id, borrow_history_data):
    return borrow_history_data[borrow_history_data['user_id'] == user_id]['book_id'].tolist()

# Recommendation function
def recommend_books(user_id, borrow_history_data):
    user_books = get_user_books(user_id, borrow_history_data)
    
    # Find other users who borrowed the same books as the target user
    other_users = borrow_history_data[borrow_history_data['book_id'].isin(user_books)]['user_id'].unique()
    other_users = [user for user in other_users if user != user_id]  # Exclude the target user

    recommended_books = []

    # For each other user, recommend books they borrowed that the target user has not borrowed
    for other_user in other_users:
        books_borrowed_by_other = get_user_books(other_user, borrow_history_data)
        recommended_books.extend(books_borrowed_by_other)
    
    # Remove books already borrowed by the target user
    recommended_books = [book for book in recommended_books if book not in user_books]
    
    # Count the occurrences of each book to find the most common recommendation
    book_counts = Counter(recommended_books)
    #print(book_counts.most_common(1))

    # Get the most common book (the one most frequently recommended)
    recommended_book = book_counts.most_common(1)[0][0] if book_counts else None
    
    return recommended_book

if __name__ == "__main__":

    # get user id from arguments 
    parser = argparse.ArgumentParser()
    parser.add_argument("user_id", type=int,  nargs="?",default=1)
    args = parser.parse_args()
    
    borrow_history_data = fetch_borrow_history()
    recommended_book = recommend_books(args.user_id, borrow_history_data)
    
    # book id for now
    print(f"Recommended book for user {args.user_id}: {recommended_book}")