package com.example.library.mapper;

import com.example.library.api.request.BorrowingRequest;
import com.example.library.api.request.BorrowingResponse;
import com.example.library.model.BorrowingRecord;
import com.example.library.model.value.BookId;
import com.example.library.model.value.DateValue;
import com.example.library.model.value.PatronId;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class BorrowingMapper {
    private BorrowingMapper() {
    }

    public static BorrowingRecord toBorrowingRecord(BorrowingRequest request) {
        return BorrowingRecord.builder()
                .id(0L)
                .bookId(BookId.of(request.bookId()))
                .patronId(PatronId.of(request.patronId()))
                .borrowingDate(DateValue.of(toOffsetDateTime(request.borrowingDate())))
                .returnDate(DateValue.of(toOffsetDateTime(request.borrowingDate())))
                .build();
    }

    public static BorrowingResponse toBorrowingResponse(BorrowingRecord borrowingRecord) {
        return BorrowingResponse
                .builder()
                .id(borrowingRecord.id())
                .build();
    }

    private static OffsetDateTime toOffsetDateTime(String dateTimeString) {
        final var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        return OffsetDateTime.parse(dateTimeString, formatter.withZone(ZoneOffset.UTC));
    }
}
