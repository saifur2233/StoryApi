package com.example.storyapi.exceptions;

public class AccessDeniedException extends RuntimeException{
    public AccessDeniedException( String key, String value) {
        super( " Access denied " + key + " " + value);
    }
}
