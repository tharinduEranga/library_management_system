package com.example.library.repository.dao;

import com.example.library.repository.entity.BookData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookData, Long> {
    boolean existsAllByIsbn(String isbn);
    boolean existsAllByIsbnAndIdNot(String isbn, Long id);
}
