package com.example.library_rest.repository;

import com.example.library_rest.model.BorrowingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowingHistoryRepository extends JpaRepository<BorrowingHistory, Long> {

    List<BorrowingHistory> findByUserId(Long userId);
}
