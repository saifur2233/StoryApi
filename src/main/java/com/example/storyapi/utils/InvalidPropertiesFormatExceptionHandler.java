package com.example.storyapi.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Component
public class InvalidPropertiesFormatExceptionHandler extends ResponseEntityExceptionHandler implements RestExceptionHandler<InvalidPropertiesFormatException>{

    @ExceptionHandler(InvalidPropertiesFormatException.class)
    @Override
    public ResponseEntity<Object> handleException(InvalidPropertiesFormatException ex) {
        ApiError apiError = new ApiError();
        apiError.message.add(ex.getMessage());
        apiError.setStatus(HttpStatus.NOT_FOUND);
        apiError.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
