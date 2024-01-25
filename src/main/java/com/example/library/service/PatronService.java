package com.example.library.service;

import com.example.library.exception.custom.BusinessRuleViolationException;
import com.example.library.exception.custom.NotFoundException;
import com.example.library.mapper.PatronMapper;
import com.example.library.model.Patron;
import com.example.library.model.value.PatronId;
import com.example.library.repository.dao.PatronRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PatronService {
    private final PatronRepository patronRepository;

    public PatronService(final PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public Patron add(final Patron patron) {
        if (patronRepository.existsByContactInformation(patron.contactInfo().mobile())) {
            throw new BusinessRuleViolationException("Contact info %s already exists"
                    .formatted(patron.contactInfo().mobile()));
        }
        var patronData = PatronMapper.toPatronData(patron);
        patronData = patronRepository.save(patronData);
        return PatronMapper.toPatron(patronData);
    }

    public Patron update(final Patron patron) {
        checkIfPatronExists(patron.id().value());
        if (patronRepository.existsByContactInformationAndIdNot(patron.contactInfo().mobile(), patron.id().value())) {
            throw new BusinessRuleViolationException("Contact info %s already exists".formatted(patron.contactInfo().mobile()));
        }
        var patronData = PatronMapper.toPatronData(patron);
        patronData = patronRepository.save(patronData);
        return PatronMapper.toPatron(patronData);
    }

    public void delete(final PatronId patronId) {
        checkIfPatronExists(patronId.value());
        patronRepository.deleteById(patronId.value());
    }

    public Patron get(final PatronId patronId) {
        return patronRepository.findById(patronId.value())
                .map(PatronMapper::toPatron)
                .orElseThrow(() -> new NotFoundException("Patron doesn't exist for id: " + patronId.value()));
    }

    public Page<Patron> getAll(Pageable pageable) {
        return patronRepository.findAll(pageable)
                .map(PatronMapper::toPatron);
    }


    private void checkIfPatronExists(Long patronId) {
        if (patronRepository.findById(patronId).isEmpty()) {
            throw new NotFoundException("Patron doesn't exist for id: " + patronId);
        }
    }
}
