package com.example.storyapi.exceptions;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public interface RestExceptionHandler<T> {
    ResponseEntity<Object> handleException(T ex);
}

