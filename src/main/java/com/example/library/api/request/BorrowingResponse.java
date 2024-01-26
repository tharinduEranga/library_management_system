package com.example.library.api.request;

import jakarta.annotation.Nonnull;

import java.util.Objects;

public record BorrowingResponse(
        @Nonnull
        Long id
) {
    public BorrowingResponse {
        Objects.requireNonNull(id, "borrowing id is required!");
    }

    public static BorrowingResponse.Builder builder() {
        return new BorrowingResponse.Builder();
    }

    public static final class Builder {
        private @Nonnull Long id;

        private Builder() {
        }

        public static BorrowingResponse.Builder builder() {
            return new BorrowingResponse.Builder();
        }

        public BorrowingResponse.Builder id(@Nonnull Long val) {
            id = val;
            return this;
        }

        public BorrowingResponse build() {
            return new BorrowingResponse(id);
        }
    }
}
