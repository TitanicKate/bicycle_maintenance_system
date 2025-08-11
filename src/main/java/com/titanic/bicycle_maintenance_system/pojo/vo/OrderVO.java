package com.titanic.bicycle_maintenance_system.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderVO {
    private String id;
    private Long userId;
    private Integer bicycleType;
    private Integer faultType;
    private String description;
    private LocalDateTime appointmentTime;
    private Long repairmanId;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer needService;
    private String serviceAddress;

}


