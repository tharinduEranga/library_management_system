package com.example.library.model;

import com.example.library.model.value.BookId;
import com.example.library.model.value.Isbn;
import jakarta.annotation.Nonnull;

import java.util.Objects;

public record Book(
        @Nonnull
        BookId id,
        @Nonnull
        String title,
        @Nonnull
        String author,
        @Nonnull
        Integer publicationYear,
        @Nonnull
        Isbn isbn,
        @Nonnull
        Boolean isAvailable
) {
    public Book {
        Objects.requireNonNull(id, "book id is required!");
        Objects.requireNonNull(title, "book title is required!");
        Objects.requireNonNull(author, "book author is required!");
        Objects.requireNonNull(publicationYear, "book publicationYear is required!");
        Objects.requireNonNull(isbn, "book isbn is required!");
        Objects.requireNonNull(isAvailable, "book availability is required!");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private @Nonnull BookId id;
        private @Nonnull String title;
        private @Nonnull String author;
        private @Nonnull Integer publicationYear;
        private @Nonnull Isbn isbn;
        private @Nonnull Boolean isAvailable;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(@Nonnull BookId val) {
            id = val;
            return this;
        }

        public Builder title(@Nonnull String val) {
            title = val;
            return this;
        }

        public Builder author(@Nonnull String val) {
            author = val;
            return this;
        }

        public Builder publicationYear(@Nonnull Integer val) {
            publicationYear = val;
            return this;
        }

        public Builder isbn(@Nonnull Isbn val) {
            isbn = val;
            return this;
        }

        public Builder isAvailable(@Nonnull Boolean val) {
            isAvailable = val;
            return this;
        }

        public Book build() {
            return new Book(id, title, author, publicationYear, isbn, isAvailable);
        }
    }
}
