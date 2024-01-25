package com.example.library.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PatronUpdateRequest(
        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotNull(message = "Contact Info cannot be null")
        String contactInfo
) {
}
