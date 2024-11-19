package com.example.library_rest.service;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;

import com.example.library_rest.model.BorrowingHistory;
import com.example.library_rest.model.Book;
import com.example.library_rest.model.User;

import com.example.library_rest.repository.BorrowingHistoryRepository;
import com.example.library_rest.repository.BookRepository;
import com.example.library_rest.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.library_rest.repository.UserRepository;

import java.util.List;

@Service
public class BorrowingHistoryService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowingHistoryRepository borrowingHistoryRepository;

    @Autowired
    public BorrowingHistoryService(BorrowingHistoryRepository borrowingHistoryRepository) {
        this.borrowingHistoryRepository = borrowingHistoryRepository;
    }

    // Retrieve borrowing history for a specific user by userId
    public List<BorrowingHistory> getBorrowHistoryByUserId(Long userId) {
        return borrowingHistoryRepository.findByUserId(userId);
    }

    
    @Transactional
    public String borrowBook(Long userId, Long bookId, LocalDate returnDate) {
        // Find user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Find book
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        // Check if copies are available
        if (book.getCopiesAvailable() <= 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No copies available for this book");
        }

        // validate on return date
        if (returnDate.isBefore(LocalDate.now()))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Return date must be after today.");

        }

        // Reduce available copies
        book.setCopiesAvailable(book.getCopiesAvailable() - 1);
        bookRepository.save(book);

        // Record borrowing history
        BorrowingHistory history = new BorrowingHistory();
        history.setUser(user);
        history.setBook(book);
        history.setBorrowDate(java.time.LocalDate.now());
        history.setReturnDate(returnDate);

        borrowingHistoryRepository.save(history);

        return "Book borrowed successfully";
    }
}
