package com.example.storyapi.exceptions;

public class PasswordNotMatchException extends RuntimeException{
    public PasswordNotMatchException(Class clazz, String key, String value) {
        super(clazz.getSimpleName() + " not match " + key + " " + value);
    }
}
