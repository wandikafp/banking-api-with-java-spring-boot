package dev.wandika.springbootdemo.user.model;

import dev.wandika.springbootdemo.base.model.BaseMapper;
import dev.wandika.springbootdemo.user.model.dto.User;
import dev.wandika.springbootdemo.user.model.entity.UserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends BaseMapper<UserEntity, User> {
    @Override
    public UserEntity convertToEntity(User dto) {
        UserEntity userEntity = new UserEntity();
        if (dto != null) {
            BeanUtils.copyProperties(dto, userEntity);
        }
        return userEntity;
    }

    @Override
    public User convertToDto(UserEntity entity) {
        User user = new User();
        if (entity != null) {
            BeanUtils.copyProperties(entity, user);
        }
        return user;
    }
}