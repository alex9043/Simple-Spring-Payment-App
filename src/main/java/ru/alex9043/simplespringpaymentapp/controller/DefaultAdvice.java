package ru.alex9043.simplespringpaymentapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.alex9043.simplespringpaymentapp.dao.error.ErrorResponse;
import ru.alex9043.simplespringpaymentapp.dao.error.MultiErrorResponse;
import ru.alex9043.simplespringpaymentapp.error.AuthenticationException;
import ru.alex9043.simplespringpaymentapp.error.PaymentNotFoundException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class DefaultAdvice {

    @ExceptionHandler(value = {AuthenticationException.class})
    private ResponseEntity<ErrorResponse> handleUserNotFoundException() {
        ErrorResponse response = new ErrorResponse(
                new Date(System.currentTimeMillis()),
                HttpStatus.UNAUTHORIZED.value(),
                "Username not found"
        );
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {PaymentNotFoundException.class})
    private ResponseEntity<ErrorResponse> handleGetException() {
        ErrorResponse response = new ErrorResponse(
                new Date(System.currentTimeMillis()),
                HttpStatus.NOT_FOUND.value(),
                "payment with this id was not found!"
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    private ResponseEntity<ErrorResponse> handleMessageNotReadableException() {
        ErrorResponse response = new ErrorResponse(
                new Date(System.currentTimeMillis()),
                HttpStatus.BAD_REQUEST.value(),
                "Sum must be a maximum of 9 digits"
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<MultiErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        MultiErrorResponse response = new MultiErrorResponse(
                new Date(System.currentTimeMillis()),
                HttpStatus.BAD_REQUEST.value(),
                errors
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
