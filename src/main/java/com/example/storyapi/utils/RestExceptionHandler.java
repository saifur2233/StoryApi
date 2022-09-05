package com.example.storyapi.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public interface RestExceptionHandler {
    ResponseEntity<Object> handleException(RuntimeException runtimeException);
}