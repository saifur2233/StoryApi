package com.example.storyapi.exceptions;

import org.springframework.util.StringUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;


public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(Class clazz, String key, String value) {
        super(clazz.getSimpleName() + " not found with " + key + " " + value);
    }
}
