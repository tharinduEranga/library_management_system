package com.example.library.model.value;

import jakarta.annotation.Nonnull;

import java.util.Objects;

public record PatronId(
        @Nonnull
        Long value
) {
    public PatronId {
        Objects.requireNonNull(value, "patron id is required!");
    }

    public static PatronId of(final Long value) {
        return new PatronId(value);
    }
}
