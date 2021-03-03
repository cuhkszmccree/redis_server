package com.example.redis_service.Service.Impl;

import com.example.redis_service.Service.RtDataService;
import com.example.redis_service.domain.RtData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RtDataImpl implements RtDataService {
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public void insertRtData(int key, int data) {
        RtData rtData = RtData.builder().key(key).Real_Time_Data(data).build();
        redisTemplate.opsForValue().set(String.valueOf(key), rtData, 1, TimeUnit.HOURS);
    }

    @Override
    public void updateRtData(int key, int val) {
        RtData rtData = RtData.builder().key(key).Real_Time_Data(val).build();
        redisTemplate.opsForValue().set(String.valueOf(key), rtData, 1, TimeUnit.HOURS);
    }


    @Override
    public RtData get_RtDataByid(String key) {
        return (RtData) redisTemplate.opsForValue().get(key);
    }

}
