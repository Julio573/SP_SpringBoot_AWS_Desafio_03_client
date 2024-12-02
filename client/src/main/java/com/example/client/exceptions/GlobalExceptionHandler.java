package com.example.client.exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<RestError> handleNoSuchElementException(NoSuchElementException ex) {
        log.error("Client not found", ex);
        RestError error = new RestError("The requested client could not be located in the system",  "CLIENT_NOT_FOUND", LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RestError> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Invalid Argument provided: {}", ex.getMessage());
        RestError error = new RestError("Invalid Argument", "BAD_REQUEST", LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RestError> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error("Method argument type mismatch: {}", ex.getMessage());
        RestError error = new RestError("Please verify the input type.", "BAD_REQUEST", LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestError> handleGeneralException(Exception ex) {
        log.error("Internal server error", ex);
        RestError error = new RestError("No such user",  "400", LocalDateTime.now());
        return new ResponseEntity<>(error, INTERNAL_SERVER_ERROR);
    }
}
