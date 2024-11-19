package com.example.library_rest.controller;

import com.example.library_rest.model.User;
import com.example.library_rest.dto.UserDTO;

import com.example.library_rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    // POST /users: Create a new user 
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody @Validated UserDTO user) {
         // TODO : return descriptive response messages
        User createdUser = new User();
        createdUser.setName(user.getName());
        createdUser.setEmail(user.getEmail());
        createdUser.setRole(user.getRoleAsEnum());

        userService.createUser(createdUser);
        return ResponseEntity.ok("User created successfully");
    }
}
