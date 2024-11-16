package com.example.library_rest.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.library_rest.model.Response;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<Response> home() {
        Response response = new Response("Welcome to the Library Management System!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
