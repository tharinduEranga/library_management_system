package com.example.library.api.controller;

import com.example.library.repository.dao.BookRepository;
import com.example.library.repository.entity.BookData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BookControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("GIVEN valid book data WHEN call add book api THEN book is added successfully")
    void addBookSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "The Catcher in the Rye",
                                    "author": "J.D. Salinger",
                                    "publicationYear": 1951,
                                    "isbn": "0-061-96436-9"
                                }
                                """)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(content().json("""
                            {
                                "title": "The Catcher in the Rye",
                                "author": "J.D. Salinger",
                                "publicationYear": 1951,
                                "isbn": "0-061-96436-9",
                                "isAvailable": true
                            }
                        """));
    }

    @Test
    @DisplayName("GIVEN valid book data with duplicated isbn WHEN call add book api THEN error is returned")
    void addBookAlreadyExists() throws Exception {
        bookRepository.save(BookData.builder()
                        .id(20L)
                        .isbn("1-061-96436-7")
                        .title("Test")
                        .publicationYear(1990)
                        .author("Test")
                        .isAvailable(Boolean.TRUE)
                .build());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "The Catcher in the Rye",
                                    "author": "J.D. Salinger",
                                    "publicationYear": 1951,
                                    "isbn": "1-061-96436-7"
                                }
                                """)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                            {
                                "errors": [
                                    {
                                        "code": "400 BAD_REQUEST",
                                        "message": "ISBN 1-061-96436-7 already exists"
                                    }
                                ]
                            }
                        """));
    }

    @Test
    @DisplayName("GIVEN valid book data WHEN call update book api THEN book is updated successfully")
    void updateBookSuccess() throws Exception {
        final var addedBook = bookRepository.save(BookData.builder()
                .id(0L)
                .isbn("1-061-92236-8")
                .title("Test update")
                .publicationYear(1995)
                .author("Test update")
                .isAvailable(Boolean.TRUE)
                .build());
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/book/%s".formatted(addedBook.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "Hollow Earth",
                                    "author": "J.K. Rowling",
                                    "publicationYear": 2000,
                                    "isbn": "1-061-96436-6",
                                    "isAvailable": false
                                }
                                """)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                            {
                                "id": %s,
                                "title": "Hollow Earth",
                                "author": "J.K. Rowling",
                                "publicationYear": 2000,
                                "isbn": "1-061-96436-6",
                                "isAvailable": false
                            }
                        """.formatted(addedBook.getId())));
    }

    @Test
    @DisplayName("GIVEN valid book data with duplicated isbn WHEN call update book api THEN error is returned")
    void updateBookAlreadyExists() throws Exception {
        bookRepository.save(BookData.builder()
                .id(21L)
                .isbn("1-061-96436-8")
                .title("Test")
                .publicationYear(1990)
                .author("Test")
                .isAvailable(Boolean.TRUE)
                .build());
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/book/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "The Catcher in the Rye",
                                    "author": "J.D. Salinger",
                                    "publicationYear": 1951,
                                    "isbn": "1-061-96436-8",
                                    "isAvailable": true
                                }
                                """)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                            {
                                "errors": [
                                    {
                                        "code": "400 BAD_REQUEST",
                                        "message": "ISBN 1-061-96436-8 already exists"
                                    }
                                ]
                            }
                        """));
    }

    @Test
    @DisplayName("GIVEN valid book id WHEN call delete book api THEN book is deleted successfully")
    void deleteBookSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/book/4")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("GIVEN invalid book id WHEN call delete book api THEN error is returned")
    void deleteBookNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/book/100")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                            {
                                "errors": [
                                    {
                                        "code": "400 BAD_REQUEST",
                                        "message": "Book doesn't exist for id: 100"
                                    }
                                ]
                            }
                        """));
    }

    @Test
    @DisplayName("GIVEN valid book id WHEN call get book api THEN book is returned successfully")
    void getBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book/2")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                            {
                                "id": 2,
                                "title": "To Kill a Mockingbird",
                                "author": "Harper Lee",
                                "publicationYear": 1960,
                                "isbn": "1-061-96436-2",
                                "isAvailable": true
                            }
                        """));
    }

    @Test
    @DisplayName("GIVEN invalid book id WHEN call get book api THEN error is returned")
    void getBookNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book/100")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                            {
                                "errors": [
                                    {
                                        "code": "400 BAD_REQUEST",
                                        "message": "Book doesn't exist for id: 100"
                                    }
                                ]
                            }
                        """));
    }

    @Test
    @DisplayName("GIVEN page and size WHEN call get all books api THEN books for the given size is returned successfully")
    void getAllBooks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book?size=2&page=0")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                            {
                                 "content": [
                                     {
                                         "id": 1,
                                         "title": "The Great Gatsby",
                                         "author": "F. Scott Fitzgerald",
                                         "publicationYear": 1925,
                                         "isbn": "1-061-96436-1",
                                         "isAvailable": true
                                     },
                                     {
                                         "id": 2,
                                         "title": "To Kill a Mockingbird",
                                         "author": "Harper Lee",
                                         "publicationYear": 1960,
                                         "isbn": "1-061-96436-2",
                                         "isAvailable": true
                                     }
                                 ],
                                 "pageable": {
                                     "pageNumber": 0,
                                     "pageSize": 2,
                                     "sort": {
                                         "empty": true,
                                         "unsorted": true,
                                         "sorted": false
                                     },
                                     "offset": 0,
                                     "paged": true,
                                     "unpaged": false
                                 },
                                 "last": false,
                                 "first": true,
                                 "size": 2,
                                 "number": 0,
                                 "sort": {
                                     "empty": true,
                                     "unsorted": true,
                                     "sorted": false
                                 },
                                 "empty": false
                             }
                        """, false));

    }
}