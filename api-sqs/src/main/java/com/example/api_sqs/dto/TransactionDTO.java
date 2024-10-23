package com.example.api_sqs.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {

    private Long id;
    private String description;
    private BigDecimal amount;
    private LocalDateTime date;
    private String status;
}