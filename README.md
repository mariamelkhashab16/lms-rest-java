
# Library Management System (LMS)
A Spring Boot application for managing library users, books, and borrowing history.

## Prerequisites
- Java 17
- Maven
- PostgreSQL
- Python 3.8 or higher

## Running the application with docker
#### 1. Clone the repository

```bash
git clone https://github.com/mariamelkhashab16/lms-rest-java
cd lms-rest-java
```
#### 2. Attach the .env.example file to the root of the project and rename it to .env
#### 3. build the docker image
```bash
docker build -t spring-boot-app .
```
you may need super user privilege
```bash
sudo docker build -t spring-boot-app .
```
#### 4. Run the docker container
```bash
 docker run --env-file .env -p 8080:8080 spring-boot-app
```

#### 5. Find the app running on http://localhost:8080 
***current state of the database is populated but feel free to run the gen_data.py
## Features checklist
### Database:
- [x]  Index on books table to optimize searching by title/author
- [x]  Index on borrowing history to optimize fetching user history
### API development
- [x]  Basic apis for creating user, adding a book, retreiving borrow history
- [x]  Some validations like: prevent borrowing if book copies = 0, ensure required fields, ..
### Python scripting
- [x]  Generate random data to seed the Database
    - Command to run the script:  
    ```bash
    python3 gen_data.py
    ```
- [x]  Export all books that haven’t been borrowed in the last 6 months to a CSV file.
    - Command to run the script:  
        ```bash
        python3 export_borrowing_data.py
        ```

### Machine learning insight (in python)
- [x]  A basic recommendation technique to recommend the next book for the user based on his borrowing history 
    - Command to run the script (user ID is optional, default is `1`):  
    ```bash
    python3 user_book_recommendation.py [user_id]
    ```
    ```bash
    python3 user_book_recommendation.py 2
    ```

## Future improvements
- [ ]  JWT authentication
- [ ]  Role based authorization
- [ ]  Implementing more API endpoints
