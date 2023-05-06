package dev.wandika.springbootdemo.transaction.service;

import dev.wandika.springbootdemo.account.model.entity.BankAccountEntity;
import dev.wandika.springbootdemo.account.repository.BankAccountRepository;
import dev.wandika.springbootdemo.notification.service.NotificationService;
import dev.wandika.springbootdemo.transaction.model.TransactionMapper;
import dev.wandika.springbootdemo.transaction.model.TransactionType;
import dev.wandika.springbootdemo.transaction.model.dto.Transaction;
import dev.wandika.springbootdemo.transaction.model.entity.TransactionEntity;
import dev.wandika.springbootdemo.transaction.model.request.CashRequest;
import dev.wandika.springbootdemo.transaction.model.request.FundTransferRequest;
import dev.wandika.springbootdemo.transaction.model.response.FundTransferResponse;
import dev.wandika.springbootdemo.transaction.repository.TransactionRepository;
import dev.wandika.springbootdemo.base.exception.EntityNotFoundException;
import dev.wandika.springbootdemo.base.exception.InsufficientFundsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class TransactionService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private NotificationService notificationService;

    public List<Transaction> getTransactionHistoryOfAccout(String accountNumber, LocalDateTime fromDate, LocalDateTime toDate) {
        if(Objects.isNull(fromDate)) {
            fromDate = toDate.minusDays(1);
        }
        List<TransactionEntity> transactions = transactionRepository.findAllByDate(accountNumber, fromDate, toDate);
        return transactionMapper.convertToDtoList(transactions);
    }
    public FundTransferResponse fundTransfer(FundTransferRequest fundTransferRequest) {
        BankAccountEntity fromBankAccount = bankAccountRepository.findByNumber(fundTransferRequest.getFromAccount()).orElseThrow(EntityNotFoundException::new);
        BankAccountEntity toBankAccount = bankAccountRepository.findByNumber(fundTransferRequest.getToAccount()).orElseThrow(EntityNotFoundException::new);

        validateBalance(fromBankAccount, fundTransferRequest.getAmount());

        String transactionId = internalFundTransfer(fromBankAccount, toBankAccount, fundTransferRequest.getAmount());
        return FundTransferResponse.builder().message("Transaction successfully completed").transactionId(transactionId).build();
    }
    public String internalFundTransfer(BankAccountEntity fromBankAccount, BankAccountEntity toBankAccount, BigDecimal amount) {
        String transactionId = UUID.randomUUID().toString();

        fromBankAccount.setBalance(fromBankAccount.getBalance().subtract(amount));
        fromBankAccount.setUpdateAt(LocalDateTime.now());
        bankAccountRepository.save(fromBankAccount);

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionType(TransactionType.FUND_TRANSFER);
        transactionEntity.setTransactionId(transactionId);
        transactionEntity.setReferenceNumber(toBankAccount.getNumber());
        transactionEntity.setAccount(fromBankAccount);
        transactionEntity.setAmount(amount.negate());
        transactionEntity.setCreatedAt(LocalDateTime.now());
        transactionRepository.save(transactionEntity);

        toBankAccount.setBalance(toBankAccount.getBalance().add(amount));
        toBankAccount.setUpdateAt(LocalDateTime.now());
        bankAccountRepository.save(toBankAccount);

        transactionEntity.setTransactionType(TransactionType.FUND_TRANSFER);
        transactionEntity.setTransactionId(transactionId);
        transactionEntity.setReferenceNumber(fromBankAccount.getNumber());
        transactionEntity.setAccount(fromBankAccount);
        transactionEntity.setAmount(amount);
        transactionEntity.setCreatedAt(LocalDateTime.now());
        transactionRepository.save(transactionEntity);

        return transactionId;
    }
    public FundTransferResponse cashTransaction(CashRequest request) {
        BankAccountEntity bankAccount = bankAccountRepository.findByNumber(request.getAccount()).orElseThrow(EntityNotFoundException::new);
        String transactionId = UUID.randomUUID().toString();
        BigDecimal resultAmount = bankAccount.getBalance().add(request.getAmount());
        if (resultAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundsException("Insufficient funds in the account " + bankAccount.getNumber(), String.valueOf(HttpStatus.PRECONDITION_FAILED));
        }
        bankAccount.setBalance(resultAmount);
        bankAccount.setUpdateAt(LocalDateTime.now());
        bankAccountRepository.save(bankAccount);
        TransactionType activity = request.getAmount().compareTo(BigDecimal.ZERO) > 0 ? TransactionType.DEPOSIT_FUND : TransactionType.WITHDRAW_FUND;
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionType(activity);
        transactionEntity.setTransactionId(transactionId);
        transactionEntity.setReferenceNumber(bankAccount.getNumber());
        transactionEntity.setAccount(bankAccount);
        transactionEntity.setAmount(request.getAmount());
        transactionEntity.setCreatedAt(LocalDateTime.now());
        transactionRepository.save(transactionEntity);
        notificationService.sendNotification(transactionId,
                "Successfully " + activity + " " + request.getAmount());
        return FundTransferResponse.builder().message("Transaction successfully completed").transactionId(transactionId).build();
    }
    private void validateBalance(BankAccountEntity bankAccount, BigDecimal amount) {
        if (bankAccount.getBalance().compareTo(BigDecimal.ZERO) < 0 || bankAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds in the account " + bankAccount.getNumber(), String.valueOf(HttpStatus.PRECONDITION_FAILED));
        }
    }
}
