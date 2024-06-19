package ru.fev.accumulation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PAServiceExceptionHandler {

    @ExceptionHandler(PAEntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(PAEntityNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(PAIncorrectArgumentException.class)
    public ResponseEntity<String> handleIncorrectArgumentException(PAIncorrectArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

}
