package ru.alex9043.simplespringpaymentapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.alex9043.simplespringpaymentapp.domain.Payment;
import ru.alex9043.simplespringpaymentapp.dto.AllPaymentsResponse;
import ru.alex9043.simplespringpaymentapp.dto.PaymentRequest;
import ru.alex9043.simplespringpaymentapp.dto.PaymentResponse;
import ru.alex9043.simplespringpaymentapp.repo.PaymentRepository;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository repository;

    public PaymentResponse getPayment(Long id) {
        Payment payment = repository.getReferenceById(id);
        return PaymentResponse.builder()
                .id(payment.getId())
                .sum(payment.getSum())
                .description(payment.getDescription())
                .date(payment.getDate())
                .build();
    }

    public AllPaymentsResponse getPayments(Integer page_num) {
        final int PER_PAGE = 6;
        Page<Payment> pageData = repository.findAll(PageRequest.of(page_num - 1, PER_PAGE));
        List<PaymentResponse> data = pageData.getContent().stream().map(payment -> PaymentResponse.builder()
                .id(payment.getId())
                .sum(payment.getSum())
                .description(payment.getDescription())
                .date(payment.getDate())
                .build()).toList();
        return AllPaymentsResponse.builder()
                .page(page_num)
                .per_page(pageData.getContent().size())
                .total(pageData.getTotalElements())
                .total_pages(pageData.getTotalPages())
                .data(data)
                .build();
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
