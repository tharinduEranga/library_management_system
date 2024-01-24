-- Insert sample data into the Book table
INSERT INTO book (title, author, publication_year, isbn)
VALUES ('The Great Gatsby', 'F. Scott Fitzgerald', 1925, '978-3-16-148410-0'),
       ('To Kill a Mockingbird', 'Harper Lee', 1960, '978-0-06-112008-4'),
       ('1984', 'George Orwell', 1949, '978-0-452-28423-4'),
       ('Pride and Prejudice', 'Jane Austen', 1813, '978-0-19-953556-9');

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
