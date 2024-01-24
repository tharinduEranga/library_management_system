package com.example.library.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record BookUpdateRequest(
        @NotBlank(message = "Title cannot be blank")
        String title,
        @NotBlank(message = "Author cannot be blank")
        String author,
        @NotNull(message = "Publication year cannot be null")
        Integer publicationYear,
        @Pattern(regexp = "\\d{1}-\\d{3}-\\d{5}-\\d{1}", message = "ISBN must be in the format x-xxx-xxxxx-x")
        String isbn
) {
}