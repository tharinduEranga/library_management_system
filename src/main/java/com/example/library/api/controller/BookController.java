package com.example.library.api.controller;

import com.example.library.api.request.BookAddRequest;
import com.example.library.api.request.BookUpdateRequest;
import com.example.library.api.response.BookResponse;
import com.example.library.logging.Log;
import com.example.library.mapper.BookMapper;
import com.example.library.model.value.BookId;
import com.example.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(path = "/api/v1/book")
public class BookController {

    private final BookService bookService;

    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

    @Log
    @PostMapping
    public ResponseEntity<BookResponse> addBook(@RequestBody @Valid BookAddRequest bookRequest) {
        final var book = BookMapper.toBook(bookRequest);
        final var addedBook = bookService.add(book);
        final var bookResponse = BookMapper.toBookResponse(addedBook);
        return ResponseEntity.created(URI.create("/api/v1/book")).body(bookResponse);
    }

    @Log
    @PutMapping(path = "/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id,
                                                   @RequestBody @Valid BookUpdateRequest bookRequest) {
        final var book = BookMapper.toBook(id, bookRequest);
        final var updatedBook = bookService.update(book);
        final var bookResponse = BookMapper.toBookResponse(updatedBook);
        return ResponseEntity.ok(bookResponse);
    }

    @Log
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.delete(BookId.of(id));
        return ResponseEntity.ok().build();
    }

    @Log
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBook(@PathVariable Long id) {
        final var book = bookService.get(new BookId(id));
        final var bookResponse = BookMapper.toBookResponse(book);
        return ResponseEntity.ok(bookResponse);
    }

    @Log
    @GetMapping
    public ResponseEntity<Page<BookResponse>> getAllBooks(Pageable pageable) {
        final var books = bookService.getAll(pageable);
        final var bookResponses = BookMapper.toBookResponses(books);
        return ResponseEntity.ok(bookResponses);
    }
}