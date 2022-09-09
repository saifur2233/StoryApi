package com.example.storyapi.utils;

public class InvalidPropertiesFormatException extends RuntimeException {
    public InvalidPropertiesFormatException(Class clazz, String key, String value) {
        super(clazz.getSimpleName() + "not valid with " + key + " " + value);
    }
}
