package com.example.library_rest.controller;

import com.example.library_rest.model.BorrowingHistory;
import com.example.library_rest.service.BorrowingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BorrowingHistoryController {

    private final BorrowingHistoryService borrowingHistoryService;

    @Autowired
    public BorrowingHistoryController(BorrowingHistoryService borrowingHistoryService) {
        this.borrowingHistoryService = borrowingHistoryService;
    }

    // Endpoint to retrieve borrowing history for a user by their userId
    @GetMapping("/borrow/history/{userId}")
    public List<BorrowingHistory> getBorrowHistory(@PathVariable Long userId) {
        return borrowingHistoryService.getBorrowHistoryByUserId(userId);
    }
}
