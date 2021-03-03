package com.example.redis_service.Config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    private RedisTemplate redisTemplate;
    @Bean
    public RedisTemplate<Object, Object> CreateReidsTemplate(RedisTemplate redisTemplate) {     //更改Redis默认序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
        this.redisTemplate = redisTemplate;
        return redisTemplate;
    }
}
