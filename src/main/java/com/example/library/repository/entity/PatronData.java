package com.example.library.repository.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "patron")
public class PatronData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String contactInformation;

    @OneToMany(mappedBy = "patron", cascade = CascadeType.REMOVE)
    private List<BorrowingRecordData> borrowingRecords;

    public PatronData() {
    }

    public PatronData(Long id, String name, String contactInformation) {
        this.id = id;
        this.name = name;
        this.contactInformation = contactInformation;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContactInformation() {
        return contactInformation;
    }


    public static final class Builder {
        private Long id;
        private String name;
        private String contactInformation;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder contactInformation(String val) {
            contactInformation = val;
            return this;
        }

        public PatronData build() {
            return new PatronData(id, name, contactInformation);
        }
    }
}