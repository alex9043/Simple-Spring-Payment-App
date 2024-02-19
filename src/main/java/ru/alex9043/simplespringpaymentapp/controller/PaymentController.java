package ru.alex9043.simplespringpaymentapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import ru.alex9043.simplespringpaymentapp.dto.AllPaymentsResponse;
import ru.alex9043.simplespringpaymentapp.dto.ErrorResponse;
import ru.alex9043.simplespringpaymentapp.dto.PaymentRequest;
import ru.alex9043.simplespringpaymentapp.dto.PaymentResponse;
import ru.alex9043.simplespringpaymentapp.error.PaymentNotFoundException;
import ru.alex9043.simplespringpaymentapp.service.PaymentService;

import java.time.LocalDateTime;
import java.util.Date;

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
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest payload) {
        return new ResponseEntity<>(service.createPayment(payload), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable("id") Long id) throws HttpClientErrorException.BadRequest {
        service.deletePayment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(value = {PaymentNotFoundException.class, HttpClientErrorException.BadRequest.class})
    private ResponseEntity<ErrorResponse> handleGetException() {
        ErrorResponse response = new ErrorResponse(
                new Date(System.currentTimeMillis()),
                HttpStatus.NOT_FOUND.value(),
                "payment with this id was not found!"
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
