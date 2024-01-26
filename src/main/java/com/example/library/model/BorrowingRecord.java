package com.example.library.model;

import com.example.library.model.value.BookId;
import com.example.library.model.value.DateValue;
import com.example.library.model.value.PatronId;
import jakarta.annotation.Nonnull;

import java.util.Objects;

public record BorrowingRecord(
        @Nonnull
        Long id,
        @Nonnull
        BookId bookId,
        @Nonnull
        PatronId patronId,
        @Nonnull
        DateValue borrowingDate,
        @Nonnull
        DateValue returnDate
) {
    public BorrowingRecord {
        Objects.requireNonNull(id, "borrowing record id is required!");
        Objects.requireNonNull(bookId, "borrowing record bookId id is required!");
        Objects.requireNonNull(patronId, "borrowing record patronId id is required!");
        Objects.requireNonNull(borrowingDate, "borrowing record borrowing date is required!");
        Objects.requireNonNull(returnDate, "borrowing record return date is required!");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private @Nonnull Long id;
        private @Nonnull BookId bookId;
        private @Nonnull PatronId patronId;
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

        public Builder bookId(@Nonnull BookId val) {
            bookId = val;
            return this;
        }

        public Builder patronId(@Nonnull PatronId val) {
            patronId = val;
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
            return new BorrowingRecord(id, bookId, patronId, borrowingDate, returnDate);
        }
    }
}
