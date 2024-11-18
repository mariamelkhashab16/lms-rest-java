package com.example.library_rest.model;

import jakarta.persistence.*;
import lombok.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
    @Index(name = "idx_title_author", columnList = "title, author")
})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Title cannot be null")
    private String title;

    @NotNull(message = "Author cannot be null")
    private String author;

    @NotNull(message = "ISBN cannot be null")
    private String isbn;

    @Column(name = "copies_available")
    private int copiesAvailable;
}
