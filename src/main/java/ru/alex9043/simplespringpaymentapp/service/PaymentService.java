package ru.alex9043.simplespringpaymentapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.alex9043.simplespringpaymentapp.domain.Payment;
import ru.alex9043.simplespringpaymentapp.dto.PaymentRequest;
import ru.alex9043.simplespringpaymentapp.dto.PaymentResponse;
import ru.alex9043.simplespringpaymentapp.repo.PaymentRepository;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository repository;

    public ResponseEntity<PaymentResponse> getPayment(Long id) {
        Payment payment = repository.getReferenceById(id);
        return ResponseEntity.ok(PaymentResponse.builder()
                .id(payment.getId())
                .sum(payment.getSum())
                .description(payment.getDescription())
                .date(payment.getDate())
                .build());
    }

    public PaymentResponse createPayment(PaymentRequest payload) {
        Payment paymentInRequest = Payment.builder()
                .sum(payload.getSum())
                .description(payload.getDescription())
                .date(payload.getDate())
                .build();
        Payment payment = repository.save(paymentInRequest);
        return PaymentResponse.builder()
                .id(payment.getId())
                .sum(payment.getSum())
                .description(payment.getDescription())
                .date(payment.getDate())
                .build();
    }
}
