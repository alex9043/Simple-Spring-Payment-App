package ru.alex9043.simplespringpaymentapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import ru.alex9043.simplespringpaymentapp.dto.*;
import ru.alex9043.simplespringpaymentapp.error.PaymentNotFoundException;
import ru.alex9043.simplespringpaymentapp.service.PaymentService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPayment(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getPayment(id));
    }

    @GetMapping
    public ResponseEntity<AllPaymentsResponse> getPayments(@RequestParam(name = "page_num", defaultValue = "1") Integer page_num) {
        return ResponseEntity.ok(service.getPayments(page_num));
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody PaymentRequest payload) {
        return new ResponseEntity<>(service.createPayment(payload), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable("id") Long id) throws HttpClientErrorException.BadRequest {
        service.deletePayment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
