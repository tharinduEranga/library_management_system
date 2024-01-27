package com.example.library.api.controller;

import com.example.library.model.Book;
import com.example.library.model.BorrowingRecord;
import com.example.library.model.value.BookId;
import com.example.library.model.value.DateValue;
import com.example.library.model.value.Isbn;
import com.example.library.model.value.PatronId;
import com.example.library.service.BookService;
import com.example.library.service.BorrowingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.OffsetDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BorrowingControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private BookService bookService;

    @Test
    @DisplayName("GIVEN valid borrowing data WHEN call borrow book api THEN book is borrowed successfully")
    void borrowBookSuccess() throws Exception {
        final var addedBook = bookService.add(Book.builder()
                .id(BookId.of(0L))
                .isbn(Isbn.of("1-061-88776-4"))
                .title("sample title")
                .author("sample author")
                .publicationYear(2001)
                .isAvailable(Boolean.TRUE)
                .build());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/borrow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                     "bookId": "%s",
                                     "patronId": 1,
                                     "borrowingDate": "2024-01-01T12:21:23",
                                     "returnDate": "2024-01-15T15:45:30"
                                 }
                                """.formatted(addedBook.id().value()))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    @DisplayName("GIVEN valid borrowing data and book is already borrowed WHEN call borrow book api THEN error is returned")
    void borrowBookUnavailable() throws Exception {
        final var addedBook = bookService.add(Book.builder()
                .id(BookId.of(0L))
                .isbn(Isbn.of("1-061-80036-4"))
                .title("sample title")
                .author("sample author")
                .publicationYear(2001)
                .isAvailable(Boolean.TRUE)
                .build());

        borrowingService.borrow(BorrowingRecord.builder()
                .id(0L)
                .bookId(addedBook.id())
                .patronId(PatronId.of(2L))
                .borrowingDate(DateValue.of(OffsetDateTime.now()))
                .returnDate(DateValue.of(OffsetDateTime.now().plusDays(5)))
                .build()
        );
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/borrow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                     "bookId": "%s",
                                     "patronId": 2,
                                     "borrowingDate": "2024-01-01T12:21:23",
                                     "returnDate": "2024-01-15T15:45:30"
                                 }
                                """.formatted(addedBook.id().value()))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                            {
                                "errors": [
                                    {
                                        "code": "400 BAD_REQUEST",
                                        "message": "Book: %s is not available"
                                    }
                                ]
                            }
                        """.formatted(addedBook.id().value())));
    }

    @Test
    @DisplayName("GIVEN valid borrowing data and invalid patron WHEN call borrow book api THEN error is returned")
    void borrowBookPatronInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/borrow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                     "bookId": 2,
                                     "patronId": 20,
                                     "borrowingDate": "2024-01-01T12:21:23",
                                     "returnDate": "2024-01-15T15:45:30"
                                 }
                                """)
                )
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                            {
                                "errors": [
                                    {
                                        "code": "404 NOT_FOUND",
                                        "message": "Patron: 20 does not exist"
                                    }
                                ]
                            }
                        """));
    }

}