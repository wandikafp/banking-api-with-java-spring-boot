package dev.wandika.springbootdemo.transaction.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class FundTransfer {
    private UUID id;
    private String transactionReference;
    private String status;
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
}
