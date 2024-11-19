package com.example.library_rest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import com.example.library_rest.model.User.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotNull(message = "Name cannot be null")
    private String name;

    @Email(message = "Email should be valid")
    @NotNull(message = "Email cannot be null")
    private String email;

    @NotNull(message = "Role cannot be null")
    private Integer role;

    public Role getRoleAsEnum() {
        return Role.fromValue(this.role); // Convert the integer value to the Role enum
    }

}

