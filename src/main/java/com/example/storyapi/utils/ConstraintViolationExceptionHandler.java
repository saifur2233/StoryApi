package com.example.storyapi.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;


@Component
@ControllerAdvice
public class ConstraintViolationExceptionHandler extends ResponseEntityExceptionHandler implements RestExceptionHandler<ConstraintViolationException> {
    @Override
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleException(ConstraintViolationException ex) {
        ApiError apiError = new ApiError();
        for (ConstraintViolation violation : ex.getConstraintViolations()) {
            apiError.message.add(violation.getPropertyPath() + ": " + violation.getMessage());
        }
        apiError.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
