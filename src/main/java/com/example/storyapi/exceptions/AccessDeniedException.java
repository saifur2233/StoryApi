package com.example.storyapi.exceptions;

public class AccessDeniedException extends RuntimeException{
    public AccessDeniedException(Class clazz, String key, String value) {
        super(clazz.getSimpleName() + " access denied " + key + " " + value);
    }
}
