package ru.alex9043.simplespringpaymentapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(defaultValue = "Ответ с информацией о платеже")
public class PaymentResponse {
    @Schema(description = "id платежа")
    private Long id;
    @Schema(description = "Сумма платежа")
    private Integer sum;
    @Schema(description = "Описание платежа")
    private String description;
    @Schema(description = "Дата платежа")
    private Date date;
}
