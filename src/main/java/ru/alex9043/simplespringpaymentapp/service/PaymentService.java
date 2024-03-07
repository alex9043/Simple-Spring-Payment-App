package ru.alex9043.simplespringpaymentapp.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.alex9043.simplespringpaymentapp.domain.Payment;
import ru.alex9043.simplespringpaymentapp.dto.AllPaymentsResponse;
import ru.alex9043.simplespringpaymentapp.dto.PaymentRequest;
import ru.alex9043.simplespringpaymentapp.dto.PaymentResponse;
import ru.alex9043.simplespringpaymentapp.error.ImageTypeInvalidException;
import ru.alex9043.simplespringpaymentapp.error.PaymentNotFoundException;
import ru.alex9043.simplespringpaymentapp.repo.PaymentRepository;

import java.io.*;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository repository;

    public PaymentResponse getPayment(Long id) {
        Payment payment = repository.findById(id).orElseThrow(PaymentNotFoundException::new);
        return PaymentResponse.builder()
                .id(payment.getId())
                .sum(payment.getSum())
                .description(payment.getDescription())
                .date(payment.getDate())
                .receipt(payment.getReceipt())
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
                .receipt(payment.getReceipt())
                .build()).toList();
        return AllPaymentsResponse.builder()
                .page(page_num)
                .per_page(pageData.getContent().size())
                .total(pageData.getTotalElements())
                .total_pages(pageData.getTotalPages())
                .data(data)
                .build();
    }

    public PaymentResponse createPayment(PaymentRequest payload) throws IOException {
        String receipt = payload.getReceipt();
        String dataType = ".jpg";

        if (receipt.startsWith("data")) {
            dataType = getImageDataType(receipt);
            receipt = receipt.split(",")[1];
        }

        String filePath = getFilePath(dataType);
        createImage(receipt, filePath);

        Payment paymentInRequest = Payment.builder()
                .sum(payload.getSum())
                .description(payload.getDescription())
                .date(payload.getDate())
                .receipt(filePath)
                .build();
        Payment payment = repository.save(paymentInRequest);
        return PaymentResponse.builder()
                .id(payment.getId())
                .sum(payment.getSum())
                .description(payment.getDescription())
                .date(payment.getDate())
                .receipt(payment.getReceipt())
                .build();
    }

    private void createImage(String receipt, String filePath) throws IOException {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(receipt);
            FileUtils.writeByteArrayToFile(new File(filePath), decodedBytes);
        } catch (IllegalArgumentException e) {
            throw new ImageTypeInvalidException();
        }

    }

    public String getImageDataType(String receipt) {
        if (receipt.startsWith("png", 11)) {
            return  ".png";
        } else if (receipt.startsWith("jpg", 11)) {
            return  ".jpg";
        } else if (receipt.startsWith("jpeg", 11)) {
            return  ".jpeg";
        } else {
            throw new ImageTypeInvalidException();
        }
    }

    public String getFilePath(String dataType) {
        return "/image/" + RandomStringUtils.randomAlphabetic(8) + dataType;
    }

    public void deletePayment(Long id) {
        Payment payment = repository.findById(id).orElseThrow(PaymentNotFoundException::new);
        repository.delete(payment);
    }
}
