package com.example.library.service;

import com.example.library.exception.custom.NotFoundException;
import com.example.library.repository.dao.BookRepository;
import com.example.library.repository.dao.BorrowingRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class ReturnService {
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookRepository bookRepository;

    public ReturnService(BorrowingRecordRepository borrowingRecordRepository, BookRepository bookRepository) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void execute(final Long borrowId) {
        var borrowRecord = borrowingRecordRepository.findById(borrowId)
                .orElseThrow(() -> new NotFoundException("No borrow record for id : %s".formatted(borrowId)));
        borrowRecord.setReturnDate(OffsetDateTime.now());
        borrowRecord = borrowingRecordRepository.save(borrowRecord);

        final var book = borrowRecord.getBook();
        book.setAvailable(Boolean.TRUE);
        bookRepository.save(book);
    }
}
