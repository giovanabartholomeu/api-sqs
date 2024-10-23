package com.example.api_sqs.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private BigDecimal amount;
    private LocalDateTime date;

    // Adiciona um status para identificar se a transação está pendente ou concluída
    private String status;
}