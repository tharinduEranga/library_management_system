package com.example.library.api.response;

import jakarta.annotation.Nonnull;

import java.util.Objects;

public record BookResponse(
        @Nonnull
        Long id,
        @Nonnull
        String title,
        @Nonnull
        String author,
        @Nonnull
        Integer publicationYear,
        @Nonnull
        String isbn
) {
    public BookResponse {
        Objects.requireNonNull(id, "book id is required!");
        Objects.requireNonNull(title, "book title is required!");
        Objects.requireNonNull(author, "book author is required!");
        Objects.requireNonNull(publicationYear, "book publicationYear is required!");
        Objects.requireNonNull(isbn, "book isbn is required!");
    }

    public static BookResponse.Builder builder() {
        return new BookResponse.Builder();
    }

    public static final class Builder {
        private @Nonnull Long id;
        private @Nonnull String title;
        private @Nonnull String author;
        private @Nonnull Integer publicationYear;
        private @Nonnull String isbn;

        private Builder() {
        }

        public static BookResponse.Builder builder() {
            return new BookResponse.Builder();
        }

        public BookResponse.Builder id(@Nonnull Long val) {
            id = val;
            return this;
        }

        public BookResponse.Builder title(@Nonnull String val) {
            title = val;
            return this;
        }

        public BookResponse.Builder author(@Nonnull String val) {
            author = val;
            return this;
        }

        public BookResponse.Builder publicationYear(@Nonnull Integer val) {
            publicationYear = val;
            return this;
        }

        public BookResponse.Builder isbn(@Nonnull String val) {
            isbn = val;
            return this;
        }

        public BookResponse build() {
            return new BookResponse(id, title, author, publicationYear, isbn);
        }
    }
}
