package dev.wandika.springbootdemo.transaction.model.dto;

import dev.wandika.springbootdemo.transaction.model.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Transaction {
    private BigDecimal amount;
    private TransactionType transactionType;
    private String referenceNumber;
    private String transactionId;
    private LocalDateTime createdAt;
    private String accountNumber;
}
