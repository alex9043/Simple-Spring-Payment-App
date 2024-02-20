package ru.alex9043.simplespringpaymentapp.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    @Digits(integer = 9, fraction = 0, message = "Sum must be a maximum of 9 digits")
    @NotNull(message = "Sum cannot be null")
    private Integer sum;
    @NotEmpty(message = "Description cannot be empty")
    @Size(max = 256, message = "Description must be a maximum of 256 characters")
    private String description;
    private Date date;
}
