package com.example.library.api.controller;

import com.example.library.api.request.PatronAddRequest;
import com.example.library.api.request.PatronUpdateRequest;
import com.example.library.api.response.PatronResponse;
import com.example.library.logging.Log;
import com.example.library.mapper.PatronMapper;
import com.example.library.model.value.PatronId;
import com.example.library.service.PatronService;
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
@RequestMapping(path = "/api/v1/patron")
public class PatronController {

    private final PatronService patronService;

    public PatronController(final PatronService patronService) {
        this.patronService = patronService;
    }

    @Log
    @PostMapping
    public ResponseEntity<PatronResponse> addPatron(@RequestBody @Valid PatronAddRequest patronRequest) {
        final var patron = PatronMapper.toPatron(patronRequest);
        final var addedPatron = patronService.add(patron);
        final var patronResponse = PatronMapper.toPatronResponse(addedPatron);
        return ResponseEntity.created(URI.create("/api/v1/patron")).body(patronResponse);
    }

    @Log
    @PutMapping(path = "/{id}")
    public ResponseEntity<PatronResponse> updatePatron(@PathVariable Long id,
                                                       @RequestBody @Valid PatronUpdateRequest patronRequest) {
        final var patron = PatronMapper.toPatron(id, patronRequest);
        final var updatedPatron = patronService.update(patron);
        final var patronResponse = PatronMapper.toPatronResponse(updatedPatron);
        return ResponseEntity.ok(patronResponse);
    }

    @Log
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable Long id) {
        patronService.delete(PatronId.of(id));
        return ResponseEntity.ok().build();
    }

    @Log
    @GetMapping("/{id}")
    public ResponseEntity<PatronResponse> getPatron(@PathVariable Long id) {
        final var patron = patronService.get(new PatronId(id));
        final var patronResponse = PatronMapper.toPatronResponse(patron);
        return ResponseEntity.ok(patronResponse);
    }

    @Log
    @GetMapping
    public ResponseEntity<Page<PatronResponse>> getAllPatrons(Pageable pageable) {
        final var patrons = patronService.getAll(pageable);
        final var patronResponses = PatronMapper.toPatronResponses(patrons);
        return ResponseEntity.ok(patronResponses);
    }
}