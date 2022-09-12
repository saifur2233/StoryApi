package com.example.storyapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler implements RestExceptionHandler<Exception>{
    @ExceptionHandler({Exception.class})
    @Override
    public ResponseEntity<Object> handleException(Exception ex) {

        ApiError apiError = new ApiError();
        apiError.message.add(ex.getMessage());
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
