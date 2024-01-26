package com.example.library.api.controller;

import com.example.library.api.request.BorrowingRequest;
import com.example.library.api.request.BorrowingResponse;
import com.example.library.mapper.BorrowingMapper;
import com.example.library.service.BorrowingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(path = "/api/v1/borrow")
public class BorrowingController {

    private final BorrowingService borrowingService;

    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @PostMapping
    public ResponseEntity<BorrowingResponse> addBook(@RequestBody @Valid BorrowingRequest borrowingRequest) {
        final var borrowingRecord = BorrowingMapper.toBorrowingRecord(borrowingRequest);
        final var borrowedRecord = borrowingService.borrow(borrowingRecord);
        final var borrowingResponse = BorrowingMapper.toBorrowingResponse(borrowedRecord);
        return ResponseEntity.created(URI.create("/api/v1/borrow")).body(borrowingResponse);
    }

}