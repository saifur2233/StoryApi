package com.example.storyapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@ControllerAdvice
public class SQLIntegrityConstraintViolationExceptionHandler extends ResponseEntityExceptionHandler{
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleException(SQLIntegrityConstraintViolationException ex) {
        List<String> error = new ArrayList<>();
        error.add(ex.getMessage());
        ApiError apiError = new ApiError();
        apiError.setMessage(error);
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}

