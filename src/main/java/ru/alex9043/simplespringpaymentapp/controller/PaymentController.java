package ru.alex9043.simplespringpaymentapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import ru.alex9043.simplespringpaymentapp.dao.error.ErrorResponse;
import ru.alex9043.simplespringpaymentapp.dao.error.MultiErrorResponse;
import ru.alex9043.simplespringpaymentapp.dao.payment.AllPaymentsResponse;
import ru.alex9043.simplespringpaymentapp.dao.payment.PaymentRequest;
import ru.alex9043.simplespringpaymentapp.dao.payment.PaymentResponse;
import ru.alex9043.simplespringpaymentapp.error.PaymentNotFoundException;
import ru.alex9043.simplespringpaymentapp.service.PaymentService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Tag(
        name = "Платежи",
        description = "Все методы для работы с платежами"
)
public class PaymentController {
    private final PaymentService service;

    @GetMapping("/{id}")
    @Operation(summary = "Получить информацию о платеже по его id")
    @ApiResponse(responseCode = "200", description = "Ответ с информацией о платеже")
    public ResponseEntity<?> getPayment(
            @Parameter(description = "id платежа")
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getPayment(id));
    }

    @GetMapping
    @Operation(summary = "Получить все платежи")
    @ApiResponse(responseCode = "200", description = "Ответ с информацией о всех платежах")
    public ResponseEntity<AllPaymentsResponse> getPayments(
            @Parameter(description = "Номер страницы с платежом")
            @RequestParam(name = "page_num", defaultValue = "1") Integer page_num) {
        return ResponseEntity.ok(service.getPayments(page_num));
    }

    @PostMapping
    @Operation(summary = "Создать новый платеж")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<PaymentResponse> createPayment(
            @Parameter(description = "Тело платежа (сумма, описание, дата")
            @Valid
            @RequestBody PaymentRequest payload) {
        return new ResponseEntity<>(service.createPayment(payload), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить платеж по id")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<?> deletePayment(
            @Parameter(description = "id платежа")
            @PathVariable("id") Long id) throws HttpClientErrorException.BadRequest {
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
