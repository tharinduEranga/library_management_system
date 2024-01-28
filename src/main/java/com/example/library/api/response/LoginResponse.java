package com.example.library.api.response;

import jakarta.annotation.Nonnull;

import java.util.Objects;

public record LoginResponse(
        @Nonnull
        String email,
        @Nonnull
        String token
) {
    public LoginResponse {
        Objects.requireNonNull(email, "email cannot be null");
        Objects.requireNonNull(token, "token cannot be null");
    }


    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private @Nonnull String email;
        private @Nonnull String token;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder email(@Nonnull String val) {
            email = val;
            return this;
        }

        public Builder token(@Nonnull String val) {
            token = val;
            return this;
        }

        public LoginResponse build() {
            return new LoginResponse(email, token);
        }
    }
}
