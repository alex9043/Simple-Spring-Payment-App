package ru.alex9043.simplespringpaymentapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(defaultValue = "Ответ с информацией о всех платежах")
public class AllPaymentsResponse {
    @Schema(description = "Текущая страница")
    private Integer page;
    @Schema(description = "Количество платежей на странице")
    private Integer per_page;
    @Schema(description = "Всего платежей")
    private Long total;
    @Schema(description = "Всего страниц")
    private Integer total_pages;
    @Schema(description = "Платежи")
    private List<PaymentResponse> data;
}
