package com.example.library_rest.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class BorrowRequest {
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Book ID cannot be null")
    private Long bookId;

    @NotNull(message = "Return date cannot be null")
    private LocalDate returnDate;

}
