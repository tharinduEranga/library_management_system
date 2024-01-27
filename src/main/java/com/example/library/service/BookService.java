package com.example.library.service;

import com.example.library.exception.custom.BusinessRuleViolationException;
import com.example.library.exception.custom.NotFoundException;
import com.example.library.mapper.BookMapper;
import com.example.library.model.Book;
import com.example.library.model.value.BookId;
import com.example.library.repository.dao.BookRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book add(final Book book) {
        if (bookRepository.existsAllByIsbn(book.isbn().value())) {
            throw new BusinessRuleViolationException("ISBN %s already exists".formatted(book.isbn().value()));
        }
        var bookData = BookMapper.toBookData(book);
        bookData = bookRepository.save(bookData);
        return BookMapper.toBook(bookData);
    }

    public Book update(final Book book) {
        checkIfBookExists(book.id().value());
        if (bookRepository.existsAllByIsbnAndIdNot(book.isbn().value(), book.id().value())) {
            throw new BusinessRuleViolationException("ISBN %s already exists".formatted(book.isbn().value()));
        }
        var bookData = BookMapper.toBookData(book);
        bookData = bookRepository.save(bookData);
        return BookMapper.toBook(bookData);
    }

    public void delete(final BookId bookId) {
        checkIfBookExists(bookId.value());
        bookRepository.deleteById(bookId.value());
    }

    @Cacheable(cacheNames = "book", key = "#bookId.value()")
    @Transactional(readOnly = true)
    public Book get(final BookId bookId) {
        return bookRepository.findById(bookId.value())
                .map(BookMapper::toBook)
                .orElseThrow(() -> new NotFoundException("Book doesn't exist for id: " + bookId.value()));
    }

    @Cacheable(cacheNames = "book")
    @Transactional(readOnly = true)
    public Page<Book> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(BookMapper::toBook);
    }


    private void checkIfBookExists(Long bookId) {
        if (bookRepository.findById(bookId).isEmpty()) {
            throw new NotFoundException("Book doesn't exist for id: " + bookId);
        }
    }
}
