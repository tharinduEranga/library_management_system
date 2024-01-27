package com.example.library.api.controller;

import com.example.library.model.Book;
import com.example.library.model.BorrowingRecord;
import com.example.library.model.value.BookId;
import com.example.library.model.value.DateValue;
import com.example.library.model.value.Isbn;
import com.example.library.model.value.PatronId;
import com.example.library.service.BookService;
import com.example.library.service.BorrowingService;
import com.example.library.service.ReturnService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ReturnControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReturnService returnService;

    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private BookService bookService;

    @Test
    @DisplayName("GIVEN valid return data WHEN call return book api THEN book is returned successfully")
    void returnBookSuccess() throws Exception {
        final var addedBook = bookService.add(Book.builder()
                .id(BookId.of(0L))
                .isbn(Isbn.of("1-044-88776-4"))
                .title("sample title")
                .author("sample author")
                .publicationYear(2001)
                .isAvailable(Boolean.TRUE)
                .build());
        final var borrowRecord = borrowingService.borrow(BorrowingRecord.builder()
                .id(0L)
                .bookId(addedBook.id())
                .patronId(PatronId.of(2L))
                .borrowingDate(DateValue.of(OffsetDateTime.now()))
                .returnDate(DateValue.of(OffsetDateTime.now().plusDays(5)))
                .build()
        );

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "borrowId": %s
                                }                             
                                """.formatted(borrowRecord.id()))
                )
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("GIVEN invalid return data WHEN call borrow book api THEN error is returned")
    void borrowBookUnavailable() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "borrowId": 115
                                }
                                """)
                )
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                            {
                                 "errors": [
                                     {
                                         "code": "404 NOT_FOUND",
                                         "message": "No borrow record for id : 115"
                                     }
                                 ]
                             }
                        """));
    }

}