package com.example.library.model.value;

import jakarta.annotation.Nonnull;

import java.util.Objects;

public record BookId(
        @Nonnull
        Long value
) {
    public BookId {
        Objects.requireNonNull(value, "book id is required!");
    }

    public static BookId of(final Long value) {
        return new BookId(value);
    }
}
