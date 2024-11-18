package com.example.library_rest.controller;

import com.example.library_rest.model.BorrowingHistory;
import com.example.library_rest.service.BorrowingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.library_rest.dto.BorrowRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/borrow")
@Validated
public class BorrowingHistoryController {

    private final BorrowingHistoryService borrowingHistoryService;

    @Autowired
    public BorrowingHistoryController(BorrowingHistoryService borrowingHistoryService) {
        this.borrowingHistoryService = borrowingHistoryService;
    }

    // Endpoint to retrieve borrowing history for a user by their userId
    @GetMapping("/history/{userId}")
    public List<BorrowingHistory> getBorrowHistory(@PathVariable Long userId) {
        return borrowingHistoryService.getBorrowHistoryByUserId(userId);
    }

    // Endpoint to borrow a book
    @PostMapping
    public ResponseEntity<String> borrowBook(@RequestBody @Validated BorrowRequest request) {
        String response = borrowingHistoryService.borrowBook(request.getUserId(), request.getBookId(), request.getReturnDate());
        return ResponseEntity.ok(response);
    }
}
