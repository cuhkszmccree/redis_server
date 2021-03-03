package com.example.redis_service.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RtData implements Serializable {
    private int key;
    private int Real_Time_Data;
}