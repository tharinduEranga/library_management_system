package com.example.library.mapper;

import com.example.library.api.request.PatronAddRequest;
import com.example.library.api.request.PatronUpdateRequest;
import com.example.library.api.response.PatronResponse;
import com.example.library.model.Patron;
import com.example.library.model.value.ContactInfo;
import com.example.library.model.value.PatronId;
import com.example.library.repository.entity.PatronData;
import org.springframework.data.domain.Page;

public class PatronMapper {

    private PatronMapper() {
    }

    public static PatronData toPatronData(final Patron patron) {
        return PatronData.builder()
                .id(patron.id().value())
                .name(patron.name())
                .contactInformation(patron.contactInfo().mobile())
                .build();
    }

    public static Patron toPatron(final PatronData patronData) {
        return Patron.builder()
                .id(PatronId.of(patronData.getId()))
                .name(patronData.getName())
                .contactInfo(ContactInfo.of(patronData.getContactInformation()))
                .build();
    }

    public static Patron toPatron(final PatronAddRequest patronRequest) {
        return Patron.builder()
                .id(PatronId.of(0L))
                .name(patronRequest.name())
                .contactInfo(ContactInfo.of(patronRequest.contactInfo()))
                .build();
    }

    public static Patron toPatron(final Long id, final PatronUpdateRequest patronRequest) {
        return Patron.builder()
                .id(PatronId.of(id))
                .name(patronRequest.name())
                .contactInfo(ContactInfo.of(patronRequest.contactInfo()))
                .build();
    }

    public static Page<PatronResponse> toPatronResponses(Page<Patron> patrons) {
        return patrons.map(PatronMapper::toPatronResponse);
    }

    public static PatronResponse toPatronResponse(final Patron patron) {
        return PatronResponse.builder()
                .id(patron.id().value())
                .name(patron.name())
                .contactInfo(patron.contactInfo().mobile())
                .build();
    }
}
