package dev.wandika.springbootdemo.account.controller;

import dev.wandika.springbootdemo.account.model.dto.BankAccount;
import dev.wandika.springbootdemo.account.service.BankAccountService;
import dev.wandika.springbootdemo.user.model.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/account")
public class AccountController {

    @Autowired
    private BankAccountService accountService;

    @PostMapping("/bank-account")
    public ResponseEntity createBankAccount(@RequestBody BankAccount request) {
        log.info("Creating user with {}", request.toString());
        return ResponseEntity.ok(accountService.createBankAccount(request));
    }

    @GetMapping("/bank-account/{account_number}")
    public ResponseEntity getBankAccount(@PathVariable("account_number") String accountNumber) {
        log.info("Reading account by ID {}", accountNumber);
        return ResponseEntity.ok(accountService.readBankAccount(accountNumber));
    }
}
