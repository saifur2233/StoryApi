package com.example.storyapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Component
public class JWTExceptionHandler extends ResponseEntityExceptionHandler implements RestExceptionHandler<JWTException> {
    @Override
    @ExceptionHandler(JWTException.class)
    public ResponseEntity<Object> handleException(JWTException ex) {
        System.out.println("Signature");
        ApiError apiError = new ApiError();
        apiError.message.add(ex.getMessage());
        apiError.setStatus(HttpStatus.UNAUTHORIZED);
        apiError.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
