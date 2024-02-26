package ru.alex9043.simplespringpaymentapp.dao.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(defaultValue = "Форма платежа")
public class PaymentRequest {
    @Digits(integer = 9, fraction = 0, message = "Sum must be a maximum of 9 digits")
    @NotNull(message = "Sum cannot be null")
    @Schema(description = "Сумма платежа")
    @Max(999999999)
    private Integer sum;
    @Schema(description = "Описание платежа")
    @Size(max = 256)
    @NotEmpty(message = "Description cannot be empty")
    @Size(max = 256, message = "Description must be a maximum of 256 characters")
    private String description;
    @Schema(description = "Дата платежа")
    private Date date;
}
