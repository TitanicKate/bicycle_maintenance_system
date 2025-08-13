package com.titanic.bicycle_maintenance_system.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EvaluationVO {
    private String id;
    private Long orderId;
    private Long userId;
    private Integer score;
    private String content;
    private LocalDateTime createTime;
}
