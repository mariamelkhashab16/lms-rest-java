package com.example.library_rest.service;

import com.example.library_rest.model.BorrowingHistory;
import com.example.library_rest.repository.BorrowingHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowingHistoryService {

    private final BorrowingHistoryRepository borrowingHistoryRepository;

    @Autowired
    public BorrowingHistoryService(BorrowingHistoryRepository borrowingHistoryRepository) {
        this.borrowingHistoryRepository = borrowingHistoryRepository;
    }

    // Retrieve borrowing history for a specific user by userId
    public List<BorrowingHistory> getBorrowHistoryByUserId(Long userId) {
        return borrowingHistoryRepository.findByUserId(userId);
    }
}
