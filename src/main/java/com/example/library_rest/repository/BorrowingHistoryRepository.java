package com.example.library_rest.repository;

import com.example.library_rest.model.BorrowingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowingHistoryRepository extends JpaRepository<BorrowingHistory, Long> {

}
