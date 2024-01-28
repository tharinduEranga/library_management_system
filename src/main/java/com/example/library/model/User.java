package com.example.library.model;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Objects;

public record User(
        @Nonnull
        String email,
        @Nonnull
        String password,
        @Nonnull
        String firstName,
        @Nullable
        String lastName
) {
    public User {
        Objects.requireNonNull(email, "email cannot be null");
        Objects.requireNonNull(password, "password cannot be null");
        Objects.requireNonNull(firstName, "first name cannot be null");
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private @Nonnull String email;
        private @Nonnull String password;
        private @Nonnull String firstName;
        private @Nullable String lastName;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder email(@Nonnull String val) {
            email = val;
            return this;
        }

        public Builder password(@Nonnull String val) {
            password = val;
            return this;
        }

        public Builder firstName(@Nonnull String val) {
            firstName = val;
            return this;
        }

        public Builder lastName(@Nullable String val) {
            lastName = val;
            return this;
        }

        public User build() {
            return new User(email, password, firstName, lastName);
        }
    }
}
