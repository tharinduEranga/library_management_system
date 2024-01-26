package com.example.library.api.request;

import jakarta.validation.constraints.NotNull;

public record ReturnRequest(
        @NotNull(message = "Borrow id cannot be null")
        Long borrowId
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private @NotNull(message = "Borrow id cannot be null") Long borrowId;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder borrowId(@NotNull(message = "Borrow id cannot be null") Long val) {
            borrowId = val;
            return this;
        }

        public ReturnRequest build() {
            return new ReturnRequest(borrowId);
        }
    }
}
