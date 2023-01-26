package org.group;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {IOException.class})
    public ResponseEntity<String> badIORequest(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student not found!");
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<String> unknownException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
