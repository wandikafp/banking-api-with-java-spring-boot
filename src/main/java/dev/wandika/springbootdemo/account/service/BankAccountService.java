package dev.wandika.springbootdemo.account.service;

import dev.wandika.springbootdemo.account.model.BankAccountMapper;
import dev.wandika.springbootdemo.account.model.dto.BankAccount;
import dev.wandika.springbootdemo.account.model.entity.BankAccountEntity;
import dev.wandika.springbootdemo.account.repository.BankAccountRepository;
import dev.wandika.springbootdemo.notification.service.NotificationService;
import dev.wandika.springbootdemo.base.exception.EntityNotFoundException;
import dev.wandika.springbootdemo.user.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BankAccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private BankAccountMapper bankAccountMapper;
    @Autowired
    private NotificationService notificationService;

    public BankAccount createBankAccount(BankAccount bankAccount) {
        BankAccountEntity save = bankAccountMapper.convertToEntity(bankAccount);
        save.setDeleted(false);
        save.setCreatedAt(LocalDateTime.now());
        save = bankAccountRepository.save(save);
        notificationService.sendNotification(save.getId().toString(),
                String.format("Bank account with account number {} created successfully", save.getNumber()));
        return bankAccountMapper.convertToDto(save);
    }

    public BankAccount readBankAccount(String accountNumber) {
        BankAccountEntity entity = bankAccountRepository.findByNumber(accountNumber).orElseThrow(EntityNotFoundException::new);
        return bankAccountMapper.convertToDto(entity);
    }
}
