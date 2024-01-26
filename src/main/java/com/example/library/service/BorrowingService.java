package com.example.library.service;

import com.example.library.exception.custom.BusinessRuleViolationException;
import com.example.library.exception.custom.NotFoundException;
import com.example.library.model.BorrowingRecord;
import com.example.library.repository.dao.BookRepository;
import com.example.library.repository.dao.BorrowingRecordRepository;
import com.example.library.repository.dao.PatronRepository;
import com.example.library.repository.entity.BorrowingRecordData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BorrowingService {
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;
    private final BorrowingRecordRepository borrowingRecordRepository;

    public BorrowingService(final BookRepository bookRepository,
                            final PatronRepository patronRepository,
                            final BorrowingRecordRepository borrowingRecordRepository) {
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
        this.borrowingRecordRepository = borrowingRecordRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public BorrowingRecord borrow(BorrowingRecord borrowingRecord) {
        final var bookData = bookRepository.findById(borrowingRecord.bookId().value())
                .orElseThrow(() -> new NotFoundException("Book: %s does not exist".formatted(borrowingRecord.bookId().value())));
        final var patronData = patronRepository.findById(borrowingRecord.patronId().value())
                .orElseThrow(() -> new NotFoundException("Patron: %s does not exist".formatted(borrowingRecord.patronId().value())));

        if (Boolean.FALSE.equals(bookData.isAvailable())) {
            throw new BusinessRuleViolationException("Book: %s is not available".formatted(bookData.getId()));
        }

        final var borrowingRecordData = BorrowingRecordData.builder()
                .book(bookData)
                .patron(patronData)
                .borrowingDate(borrowingRecord.borrowingDate().value())
                .returnDate(borrowingRecord.returnDate().value())
                .build();
        final var borrowedRecordData = borrowingRecordRepository.save(borrowingRecordData);

        bookData.setAvailable(Boolean.FALSE);
        bookRepository.save(bookData);

        return BorrowingRecord.builder()
                .id(borrowedRecordData.getId())
                .bookId(borrowingRecord.bookId())
                .patronId(borrowingRecord.patronId())
                .borrowingDate(borrowingRecord.borrowingDate())
                .returnDate(borrowingRecord.returnDate())
                .build();
    }
}
