package ru.alex9043.simplespringpaymentapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.alex9043.simplespringpaymentapp.dto.ErrorResponse;

import java.util.Date;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException() {
        ErrorResponse response = new ErrorResponse(
                new Date(System.currentTimeMillis()),
                HttpStatus.NOT_FOUND.value(),
                "Resource not found"
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
