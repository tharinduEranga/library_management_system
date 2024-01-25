package com.example.library.api.response;

import jakarta.annotation.Nonnull;

import java.util.Objects;

public record PatronResponse(
        @Nonnull
        Long id,
        @Nonnull
        String name,
        @Nonnull
        String contactInfo
) {
    public PatronResponse {
        Objects.requireNonNull(id, "patron id is required!");
        Objects.requireNonNull(name, "patron name is required!");
        Objects.requireNonNull(contactInfo, "patron contact info is required!");
    }


    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private @Nonnull Long id;
        private @Nonnull String name;
        private @Nonnull String contactInfo;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(@Nonnull Long val) {
            id = val;
            return this;
        }

        public Builder name(@Nonnull String val) {
            name = val;
            return this;
        }

        public Builder contactInfo(@Nonnull String val) {
            contactInfo = val;
            return this;
        }

        public PatronResponse build() {
            return new PatronResponse(id, name, contactInfo);
        }
    }
}
