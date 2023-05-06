package dev.wandika.springbootdemo.account.model;

import dev.wandika.springbootdemo.account.model.dto.BankAccount;
import dev.wandika.springbootdemo.account.model.entity.BankAccountEntity;
import dev.wandika.springbootdemo.base.model.BaseMapper;
import dev.wandika.springbootdemo.user.model.entity.UserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class BankAccountMapper extends BaseMapper<BankAccountEntity, BankAccount> {
    @Override
    public BankAccountEntity convertToEntity(BankAccount dto) {
        BankAccountEntity entity = new BankAccountEntity();
        if (dto != null) {
            BeanUtils.copyProperties(dto, entity, "user");
            entity.setUser(new UserEntity());
            entity.getUser().setId(dto.getUserId());
        }

        return entity;
    }

    @Override
    public BankAccount convertToDto(BankAccountEntity entity) {
        BankAccount dto = new BankAccount();
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto);
        }
        return dto;
    }
}
