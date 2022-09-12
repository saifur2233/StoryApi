package com.example.storyapi.exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(Class clazz, String key, String value) {
        super(clazz.getSimpleName() + "not valid with " + key + " " + value);
    }
}
