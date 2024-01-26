package com.example.library.repository.dao;

import com.example.library.repository.entity.BorrowingRecordData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecordData, Long> {
}
