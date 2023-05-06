package dev.wandika.springbootdemo.notification.model;

import dev.wandika.springbootdemo.notification.model.dto.NotificationMessage;
import dev.wandika.springbootdemo.notification.model.entity.NotificationDocument;
import dev.wandika.springbootdemo.base.model.BaseMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper extends BaseMapper<NotificationDocument, NotificationMessage> {
    @Override
    public NotificationDocument convertToEntity(NotificationMessage dto) {
        NotificationDocument entity = new NotificationDocument();
        if (dto != null) {
            BeanUtils.copyProperties(dto, entity);
        }
        return entity;
    }

    @Override
    public NotificationMessage convertToDto(NotificationDocument entity) {
        NotificationMessage dto = new NotificationMessage();
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto);
        }
        return dto;
    }
}
