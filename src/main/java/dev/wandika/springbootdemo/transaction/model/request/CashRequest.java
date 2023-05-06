package dev.wandika.springbootdemo.transaction.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CashRequest {
    private String account;
    private BigDecimal amount;
}
