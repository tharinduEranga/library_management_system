package com.example.library.model.value;

import jakarta.annotation.Nonnull;

import java.time.OffsetDateTime;
import java.util.Objects;

public record DateValue(
        @Nonnull
        OffsetDateTime value
) {
    public DateValue {
        Objects.requireNonNull(value, "date is required!");
    }

    public static DateValue of(final OffsetDateTime value) {
        return new DateValue(value);
    }
}
