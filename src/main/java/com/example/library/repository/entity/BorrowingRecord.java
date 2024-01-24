package com.example.library.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;

@Entity
@Table(name = "borrowing_record")
public class BorrowingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @ManyToOne
    @JoinColumn(name = "patron_id")
    private Patron patron;

    private OffsetDateTime borrowingDate;
    private OffsetDateTime returnDate;

    public BorrowingRecord() {
    }

    public BorrowingRecord(Long id, Book book, Patron patron, OffsetDateTime borrowingDate, OffsetDateTime returnDate) {
        this.id = id;
        this.book = book;
        this.patron = patron;
        this.borrowingDate = borrowingDate;
        this.returnDate = returnDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public Patron getPatron() {
        return patron;
    }

    public OffsetDateTime getBorrowingDate() {
        return borrowingDate;
    }

    public OffsetDateTime getReturnDate() {
        return returnDate;
    }


    public static final class Builder {
        private Long id;
        private Book book;
        private Patron patron;
        private OffsetDateTime borrowingDate;
        private OffsetDateTime returnDate;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder book(Book val) {
            book = val;
            return this;
        }

        public Builder patron(Patron val) {
            patron = val;
            return this;
        }

        public Builder borrowingDate(OffsetDateTime val) {
            borrowingDate = val;
            return this;
        }

        public Builder returnDate(OffsetDateTime val) {
            returnDate = val;
            return this;
        }

        public BorrowingRecord build() {
            return new BorrowingRecord(id, book, patron, borrowingDate, returnDate);
        }
    }
}