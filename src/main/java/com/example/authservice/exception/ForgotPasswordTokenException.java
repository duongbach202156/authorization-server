package com.example.authservice.exception;

public class ForgotPasswordTokenException extends RuntimeException{
    public ForgotPasswordTokenException(String message) {
        super(message);
    }
}
