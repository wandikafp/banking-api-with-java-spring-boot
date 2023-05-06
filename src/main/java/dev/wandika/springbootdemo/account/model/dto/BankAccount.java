package dev.wandika.springbootdemo.account.model.dto;

import dev.wandika.springbootdemo.account.model.AccountStatus;
import dev.wandika.springbootdemo.account.model.AccountType;
import dev.wandika.springbootdemo.user.model.dto.User;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class BankAccount {
    private UUID id;
    private String number;
    private AccountType type;
    private AccountStatus status;
    private BigDecimal balance;
    private UUID userId;
}
