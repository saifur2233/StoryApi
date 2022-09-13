package com.example.storyapi.exceptions;

public class DuplicateEmailException extends RuntimeException{
    public DuplicateEmailException(Class clazz, String key, String value) {
        super(clazz.getSimpleName() + "Already Exist Duplicate " + key + " " + value);
    }
}
