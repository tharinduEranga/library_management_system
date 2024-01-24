package com.example.library.model;

import com.example.library.model.value.DateValue;
import jakarta.annotation.Nonnull;

import java.util.Objects;

public record BorrowingRecord(
        @Nonnull
        Long id,
        @Nonnull
        Book book,
        @Nonnull
        Patron patron,
        @Nonnull
        DateValue borrowingDate,
        @Nonnull
        DateValue returnDate
) {
    public BorrowingRecord {
        Objects.requireNonNull(id, "borrowing record id is required!");
        Objects.requireNonNull(book, "borrowing record book is required!");
        Objects.requireNonNull(patron, "borrowing record patron is required!");
        Objects.requireNonNull(borrowingDate, "borrowing record borrowing date is required!");
        Objects.requireNonNull(returnDate, "borrowing record return date is required!");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private @Nonnull Long id;
        private @Nonnull Book book;
        private @Nonnull Patron patron;
        private @Nonnull DateValue borrowingDate;
        private @Nonnull DateValue returnDate;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(@Nonnull Long val) {
            id = val;
            return this;
        }

        public Builder book(@Nonnull Book val) {
            book = val;
            return this;
        }

        public Builder patron(@Nonnull Patron val) {
            patron = val;
            return this;
        }

        public Builder borrowingDate(@Nonnull DateValue val) {
            borrowingDate = val;
            return this;
        }

        public Builder returnDate(@Nonnull DateValue val) {
            returnDate = val;
            return this;
        }

        public BorrowingRecord build() {
            return new BorrowingRecord(id, book, patron, borrowingDate, returnDate);
        }
    }
}
