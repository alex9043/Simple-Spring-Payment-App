package ru.alex9043.simplespringpaymentapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex9043.simplespringpaymentapp.dto.AllPaymentsResponse;
import ru.alex9043.simplespringpaymentapp.dto.PaymentRequest;
import ru.alex9043.simplespringpaymentapp.dto.PaymentResponse;
import ru.alex9043.simplespringpaymentapp.service.PaymentService;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService service;

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable("id") Long id) {
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
}
