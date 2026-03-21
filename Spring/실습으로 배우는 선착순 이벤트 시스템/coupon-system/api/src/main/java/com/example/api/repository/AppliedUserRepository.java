package com.example.api.repository;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AppliedUserRepository {

    private final StringRedisTemplate redisTemplate;

    public AppliedUserRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Long add(Long userId) {
        return redisTemplate.opsForSet().add("applied-user", userId.toString());
    }
}
