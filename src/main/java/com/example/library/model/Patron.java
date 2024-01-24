package com.example.library.model;

import com.example.library.model.value.ContactInfo;
import com.example.library.model.value.PatronId;
import jakarta.annotation.Nonnull;

import java.util.Objects;

public record Patron(
        @Nonnull
        PatronId id,
        @Nonnull
        String name,
        @Nonnull
        ContactInfo contactInfo
) {
    public Patron {
        Objects.requireNonNull(id, "patron id is required!");
        Objects.requireNonNull(name, "patron name is required!");
        Objects.requireNonNull(contactInfo, "patron contact info is required!");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private @Nonnull PatronId id;
        private @Nonnull String name;
        private @Nonnull ContactInfo contactInfo;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(@Nonnull PatronId val) {
            id = val;
            return this;
        }

        public Builder name(@Nonnull String val) {
            name = val;
            return this;
        }

        public Builder contactInfo(@Nonnull ContactInfo val) {
            contactInfo = val;
            return this;
        }

        public Patron build() {
            return new Patron(id, name, contactInfo);
        }
    }
}
