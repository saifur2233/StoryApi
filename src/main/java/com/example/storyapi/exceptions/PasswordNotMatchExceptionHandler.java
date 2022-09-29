package com.example.storyapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Component
public class PasswordNotMatchExceptionHandler extends ResponseEntityExceptionHandler implements RestExceptionHandler<PasswordNotMatchException>{
    @ExceptionHandler(PasswordNotMatchException.class)
    @Override
    public ResponseEntity<Object> handleException(PasswordNotMatchException ex) {
        ApiError apiError = new ApiError();
        apiError.message.add(ex.getMessage());
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
