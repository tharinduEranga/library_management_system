package com.example.library.repository.dao;

import com.example.library.repository.entity.PatronData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatronRepository extends JpaRepository<PatronData, Long> {

}
