package ru.alex9043.simplespringpaymentapp.dao.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultiErrorResponse {
    @JsonFormat(timezone = "Europe/Moscow")
    private Date timestamp;
    private Integer status;
    private Map<String, String> messages;
}
