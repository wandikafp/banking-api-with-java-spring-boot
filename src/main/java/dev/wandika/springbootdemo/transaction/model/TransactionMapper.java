package dev.wandika.springbootdemo.transaction.model;

import dev.wandika.springbootdemo.account.model.entity.BankAccountEntity;
import dev.wandika.springbootdemo.base.model.BaseMapper;
import dev.wandika.springbootdemo.transaction.model.dto.Transaction;
import dev.wandika.springbootdemo.transaction.model.entity.TransactionEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper extends BaseMapper<TransactionEntity, Transaction> {
    @Override
    public TransactionEntity convertToEntity(Transaction dto) {
        TransactionEntity entity = new TransactionEntity();
        if (dto != null) {
            BeanUtils.copyProperties(dto, entity, "account");
            entity.setAccount(new BankAccountEntity());
            entity.getAccount().setNumber(dto.getAccountNumber());
        }
        return entity;
    }

    @Override
    public Transaction convertToDto(TransactionEntity entity) {
        Transaction dto = new Transaction();
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto);
            dto.setAccountNumber(entity.getAccount().getNumber());
        }
        return dto;
    }
}
