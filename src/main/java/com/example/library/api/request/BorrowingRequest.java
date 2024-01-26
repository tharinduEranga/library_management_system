package com.example.library.api.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record BorrowingRequest(
        @NotNull(message = "Book id cannot be null")
        Long bookId,
        @NotNull(message = "Patron id cannot be null")
        Long patronId,
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}",
                message = "Borrowing date must be in the format yyyy-MM-ddTHH:mm:ss")
        String borrowingDate,
        @NotNull(message = "Return date cannot be null")
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}",
                message = "Return date must be in the format yyyy-MM-ddTHH:mm:ss")
        String returnDate
) {
}
