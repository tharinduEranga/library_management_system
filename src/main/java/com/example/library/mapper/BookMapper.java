package com.example.library.mapper;

import com.example.library.api.request.BookAddRequest;
import com.example.library.api.request.BookUpdateRequest;
import com.example.library.api.response.BookResponse;
import com.example.library.model.Book;
import com.example.library.model.value.BookId;
import com.example.library.model.value.Isbn;
import com.example.library.repository.entity.BookData;
import org.springframework.data.domain.Page;

public class BookMapper {

    private BookMapper() {
    }

    public static BookData toBookData(final Book book) {
        return BookData.builder()
                .id(book.id().value())
                .author(book.author())
                .title(book.title())
                .isbn(book.isbn().value())
                .publicationYear(book.publicationYear())
                .isAvailable(book.isAvailable())
                .build();
    }

    public static Book toBook(final BookData bookData) {
        return Book.builder()
                .id(BookId.of(bookData.getId()))
                .author(bookData.getAuthor())
                .title(bookData.getTitle())
                .isbn(Isbn.of(bookData.getIsbn()))
                .publicationYear(bookData.getPublicationYear())
                .isAvailable(bookData.isAvailable())
                .build();
    }

    public static Book toBook(final BookAddRequest bookRequest) {
        return Book.builder()
                .id(BookId.of(0L))
                .author(bookRequest.author())
                .title(bookRequest.title())
                .isbn(Isbn.of(bookRequest.isbn()))
                .publicationYear(bookRequest.publicationYear())
                .isAvailable(Boolean.TRUE)
                .build();
    }

    public static Book toBook(final Long id, final BookUpdateRequest bookRequest) {
        return Book.builder()
                .id(BookId.of(id))
                .author(bookRequest.author())
                .title(bookRequest.title())
                .isbn(Isbn.of(bookRequest.isbn()))
                .publicationYear(bookRequest.publicationYear())
                .isAvailable(bookRequest.isAvailable())
                .build();
    }

    public static Page<BookResponse> toBookResponses(Page<Book> books) {
        return books.map(BookMapper::toBookResponse);
    }

    public static BookResponse toBookResponse(final Book book) {
        return BookResponse.builder()
                .id(book.id().value())
                .author(book.author())
                .title(book.title())
                .isbn(book.isbn().value())
                .publicationYear(book.publicationYear())
                .isAvailable(book.isAvailable())
                .build();
    }
}
