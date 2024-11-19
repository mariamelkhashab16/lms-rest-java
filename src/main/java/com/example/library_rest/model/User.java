package com.example.library_rest.model;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Email;

@Entity
@Table(name = "\"User\"") // Case-sensitive
@Inheritance(strategy = InheritanceType.JOINED) // Joined --> normalized schema
@Data // Lombok 
@NoArgsConstructor 
@AllArgsConstructor 
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @Email(message = "Email should be valid")
    private String email;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role cannot be null")
    private Role role;

public enum Role {
    ADMIN(0),
    PATRON(1);

    private final int value;

    // Constructor
    Role(int value) {
        this.value = value;
    }

    // Getter method to retrieve the integer value
    public int getValue() {
        return value;
    }

    public static Role fromValue(int value) {
        for (Role role : Role.values()) {
            if (role.getValue() == value) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unexpected value: " + value);
    }
}
}