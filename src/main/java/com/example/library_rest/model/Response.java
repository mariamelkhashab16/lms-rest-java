package com.example.library_rest.model;

public class Response {
    private String message;

    // Constructor
    public Response(String message) {
        this.message = message;
    }

    // Getter
    public String getMessage() {
        return message;
    }

    // Setter
    public void setMessage(String message) {
        this.message = message;
    }
}
