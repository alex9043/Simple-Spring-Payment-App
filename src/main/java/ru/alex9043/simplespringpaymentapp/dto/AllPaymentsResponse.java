package ru.alex9043.simplespringpaymentapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllPaymentsResponse {
    private Integer page;
    private Integer per_page;
    private Long total;
    private Integer total_pages;
    private List<PaymentResponse> data;
}
