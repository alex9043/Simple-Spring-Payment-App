package ru.alex9043.simplespringpaymentapp.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.alex9043.simplespringpaymentapp.dao.error.ErrorResponse;

import java.util.Date;

@ControllerAdvice
public class SecurityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({AuthenticationException.class})
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleAuthenticationException(Exception ex) {
        ErrorResponse re = new ErrorResponse(
                new Date(System.currentTimeMillis()),
                HttpStatus.UNAUTHORIZED.value(),
                "Authentication error. Check your JWT"
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(re);
    }
}
