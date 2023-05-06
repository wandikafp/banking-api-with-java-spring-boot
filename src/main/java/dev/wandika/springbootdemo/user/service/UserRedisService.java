package dev.wandika.springbootdemo.user.service;

import dev.wandika.springbootdemo.user.model.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserRedisService {
    @Autowired
    private RedisTemplate<String, User> redisTemplate;

    public User getByKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setValueByKey(String key, User user) {
        redisTemplate.opsForValue().set(key, user);
    }

    public boolean isKeyExists(String key) {
        return redisTemplate.hasKey(key);
    }
}
