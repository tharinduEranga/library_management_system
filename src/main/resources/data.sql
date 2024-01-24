-- Insert sample data into the Book table
INSERT INTO book (title, author, publication_year, isbn)
VALUES ('The Great Gatsby', 'F. Scott Fitzgerald', 1925, '1-061-96436-1'),
       ('To Kill a Mockingbird', 'Harper Lee', 1960, '1-061-96436-2'),
       ('1984', 'George Orwell', 1949, '1-061-96436-3'),
       ('Pride and Prejudice', 'Jane Austen', 1813, '1-061-96436-4');

-- Insert sample data into the Patron table
INSERT INTO patron (name, contact_information)
VALUES ('Alice Johnson', 'alice@example.com'),
       ('Bob Smith', 'bob@example.com'),
       ('Charlie Brown', 'charlie@example.com'),
       ('David Miller', 'david@example.com');

-- Insert sample data into the Borrowing_Record table
INSERT INTO borrowing_record (book_id, patron_id, borrowing_date, return_date)
VALUES (1, 1, '2024-01-01', '2024-01-15'),
       (2, 2, '2024-02-01', '2024-02-15'),
       (3, 3, '2024-03-01', '2024-03-15'),
       (4, 4, '2024-04-01', '2024-04-15');
