package com.example.storyapi.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;

@Component
public class EntityNotFoundExceptionHandler extends ResponseEntityExceptionHandler implements RestExceptionHandler<EntityNotFoundException>{
    @Override
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleException(EntityNotFoundException runtimeException) {
        ApiError apiError = new ApiError();
        apiError.message.add(runtimeException.getMessage());
        apiError.setStatus(HttpStatus.NOT_FOUND);
        apiError.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
