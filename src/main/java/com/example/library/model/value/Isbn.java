package com.example.library.model.value;

import jakarta.annotation.Nonnull;

import java.util.Objects;

public record Isbn(
        @Nonnull
        String value
) {
    public Isbn {
        Objects.requireNonNull(value, "ISBN is required!");
    }

    public static Isbn of(final String value) {
        return new Isbn(value);
    }
}
