package com.example.redis_service.Service;

import com.example.redis_service.domain.RtData;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RtDataService {

    void insertRtData(int key, int data);

    void updateRtData(int key, int val);

    RtData get_RtDataByid(String key);

}

