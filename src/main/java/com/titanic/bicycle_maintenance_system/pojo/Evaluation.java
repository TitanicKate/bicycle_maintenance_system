package com.titanic.bicycle_maintenance_system.pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评价实体类
 */
@Data
@Table(name = "t_evaluation")
@Entity
public class Evaluation {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 订单ID（外键，关联订单表）
     */
    @Column(name = "order_id")
    private Long orderId;

    /**
     * 评价用户ID（外键，关联用户表）
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 评分：1-5分
     */
    @Column(name = "score")
    private Integer score;

    /**
     * 评价内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;

    // 评分常量定义
    public static final Integer SCORE_1 = 1;    // 1分
    public static final Integer SCORE_2 = 2;    // 2分
    public static final Integer SCORE_3 = 3;    // 3分
    public static final Integer SCORE_4 = 4;    // 4分
    public static final Integer SCORE_5 = 5;    // 5分
}