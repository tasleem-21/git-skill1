package com.klu.skill9.exception;

import java.time.LocalDateTime;

public class ErrorResponse {

    private LocalDateTime timestamp;
    private String message;
    private int statusCode;

    public ErrorResponse(String message, int statusCode) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.statusCode = statusCode;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public String getMessage() { return message; }
    public int getStatusCode() { return statusCode; }
}