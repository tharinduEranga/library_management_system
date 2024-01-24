package com.example.library.model.value;

import jakarta.annotation.Nonnull;

import java.util.Objects;

public record ContactInfo(
        @Nonnull
        String mobile
) {
    public ContactInfo {
        Objects.requireNonNull(mobile, "mobile is required!");
    }

    public static ContactInfo of(final String  value) {
        return new ContactInfo(value);
    }
}
