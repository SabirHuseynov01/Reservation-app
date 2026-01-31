package com.example.reservation.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    public String handleConflictException(ConflictException ex){
        return ResponseEntity.status(409).body(ex.getMessage()).toString();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body("Validation error");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadReq(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}

