package dev.wandika.springbootdemo.user.service;

import dev.wandika.springbootdemo.notification.service.NotificationService;
import dev.wandika.springbootdemo.user.model.UserMapper;
import dev.wandika.springbootdemo.base.exception.EntityNotFoundException;
import dev.wandika.springbootdemo.user.model.dto.User;
import dev.wandika.springbootdemo.user.model.entity.UserEntity;
import dev.wandika.springbootdemo.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRedisService userRedisService;
    public User createUser(User user) {
        UserEntity save = userMapper.convertToEntity(user);
        save.setDeleted(false);
        save.setCreatedAt(LocalDateTime.now());
        save = userRepository.save(save);
        notificationService.sendNotification(save.getId().toString(), "User " + save.getEmail() + " create successfully");
        return userMapper.convertToDto(save);
    }

    public List<User> readUsers(Pageable pageable) {
        Page<UserEntity> allUsersInDb = userRepository.findAll(pageable);
        return userMapper.convertToDtoList(allUsersInDb.getContent());
    }

    public List<User> findByBalance(BigDecimal fromBalance, BigDecimal toBalance) {
        if(Objects.isNull(fromBalance)) {
            fromBalance = BigDecimal.ZERO;
        }
        if(Objects.isNull(toBalance)) {
            toBalance = BigDecimal.valueOf(Double.MAX_VALUE);
        }
        return userMapper.convertToDtoList(userRepository.findByBalance(fromBalance, toBalance));
    }

    public User readUser(UUID userId) {
        String key = "user/" + userId.toString();
        if(userRedisService.isKeyExists(key)) {
            return userRedisService.getByKey(key);
        }
        User user = userMapper.convertToDto(userRepository.findById(userId.toString()).orElse(null));
        if(Objects.nonNull(user)) {
            userRedisService.setValueByKey(key, user);
        }
        return user;
    }

    public User updateUser(User user) {
        UserEntity userEntity = userRepository.findById(user.getId()).orElseThrow(EntityNotFoundException::new);
        return userMapper.convertToDto(userRepository.save(userEntity));
    }
}
