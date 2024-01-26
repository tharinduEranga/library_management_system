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
@Table(name = "book")
public class BookData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private int publicationYear;
    @Column(unique = true, nullable = false)
    private String isbn;
    @Column(nullable = false)
    private Boolean isAvailable = Boolean.TRUE;

    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
    private List<BorrowingRecordData> borrowingRecords;

    public BookData() {
    }

    public BookData(Long id, String title, String author, int publicationYear, String isbn, Boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.isAvailable = isAvailable;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public Boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        this.isAvailable = available;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private String title;
        private String author;
        private int publicationYear;
        private String isbn;
        private Boolean isAvailable;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder author(String val) {
            author = val;
            return this;
        }

        public Builder publicationYear(int val) {
            publicationYear = val;
            return this;
        }

        public Builder isbn(String val) {
            isbn = val;
            return this;
        }

        public Builder isAvailable(Boolean val) {
            isAvailable = val;
            return this;
        }

        public BookData build() {
            return new BookData(id, title, author, publicationYear, isbn, isAvailable);
        }
    }
}