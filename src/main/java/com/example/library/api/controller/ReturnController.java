package com.example.library.api.controller;

import com.example.library.api.request.BorrowingResponse;
import com.example.library.api.request.ReturnRequest;
import com.example.library.service.ReturnService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/return")
public class ReturnController {

    private final ReturnService returnService;

    public ReturnController(ReturnService returnService) {
        this.returnService = returnService;
    }

    @PutMapping
    public ResponseEntity<BorrowingResponse> addBook(@RequestBody @Valid ReturnRequest returnRequest) {
        returnService.execute(returnRequest.borrowId());
        return ResponseEntity.ok().build();
    }
}