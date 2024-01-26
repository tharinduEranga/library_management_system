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
public class BorrowingRecordData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookData book;
    @ManyToOne
    @JoinColumn(name = "patron_id")
    private PatronData patron;

    private OffsetDateTime borrowingDate;
    private OffsetDateTime returnDate;

    public BorrowingRecordData() {
    }

    public BorrowingRecordData(Long id, BookData book, PatronData patron, OffsetDateTime borrowingDate, OffsetDateTime returnDate) {
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

    public BookData getBook() {
        return book;
    }

    public PatronData getPatron() {
        return patron;
    }

    public OffsetDateTime getBorrowingDate() {
        return borrowingDate;
    }

    public OffsetDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(OffsetDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public static final class Builder {
        private Long id;
        private BookData book;
        private PatronData patron;
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

        public Builder book(BookData val) {
            book = val;
            return this;
        }

        public Builder patron(PatronData val) {
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

        public BorrowingRecordData build() {
            return new BorrowingRecordData(id, book, patron, borrowingDate, returnDate);
        }
    }
}