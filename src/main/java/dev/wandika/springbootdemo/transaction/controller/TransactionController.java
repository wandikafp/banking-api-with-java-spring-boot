package dev.wandika.springbootdemo.transaction.controller;

import dev.wandika.springbootdemo.transaction.model.request.CashRequest;
import dev.wandika.springbootdemo.transaction.model.request.FundTransferRequest;
import dev.wandika.springbootdemo.transaction.service.FundTransferService;
import dev.wandika.springbootdemo.transaction.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/transaction")
public class TransactionController {

    @Autowired
    private FundTransferService fundTransferService;
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/history/{number}")
    public ResponseEntity getTransactionHistory(@PathVariable("number") String number,
                                                @RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
                                                @RequestParam(value = "toDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime toDate) {
        log.info("Reading account by ID {}", number);
        log.info("toDate", toDate.toString());
        return ResponseEntity.ok(transactionService.getTransactionHistoryOfAccout(number, fromDate, toDate));
    }

    @PostMapping("/fund-transfer")
    public ResponseEntity fundTransfer(@RequestBody FundTransferRequest fundTransferRequest) {

        log.info("Fund transfer initiated  from {}", fundTransferRequest.toString());
        return ResponseEntity.ok(fundTransferService.fundTransfer(fundTransferRequest));
    }
    @PostMapping("/cash")
    public ResponseEntity deposit(@RequestBody CashRequest cashRequest) {
        log.info("Fund transfer initiated  from {}", cashRequest.toString());
        return ResponseEntity.ok(transactionService.cashTransaction(cashRequest));
    }
}

