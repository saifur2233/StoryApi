package com.example.storyapi.exceptions;

public class JWTException extends RuntimeException{
    public JWTException( String message) {
        super(message);
    }
}
