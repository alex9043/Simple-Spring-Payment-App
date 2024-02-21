package ru.alex9043.simplespringpaymentapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    @JsonFormat(timezone = "Europe/Moscow")
    private Date timestamp;
    private Integer status;
    private String message;
}
