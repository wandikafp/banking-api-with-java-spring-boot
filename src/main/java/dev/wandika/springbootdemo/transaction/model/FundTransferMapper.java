package dev.wandika.springbootdemo.transaction.model;

import dev.wandika.springbootdemo.transaction.model.dto.FundTransfer;
import dev.wandika.springbootdemo.transaction.model.entity.FundTransferEntity;
import dev.wandika.springbootdemo.base.model.BaseMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class FundTransferMapper extends BaseMapper<FundTransferEntity, FundTransfer> {
    @Override
    public FundTransferEntity convertToEntity(FundTransfer dto) {
        FundTransferEntity entity = new FundTransferEntity();
        if (dto != null) {
            BeanUtils.copyProperties(dto, entity);
        }
        return entity;
    }

    @Override
    public FundTransfer convertToDto(FundTransferEntity entity) {
        FundTransfer dto = new FundTransfer();
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto);
        }
        return dto;
    }
}
