package com.titanic.bicycle_maintenance_system.pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 订单实体类
 * 对应数据库中的order表
 */
@Data
@Table(name = "t_order")
@Entity
public class Order {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 用户ID（外键）
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 自行车类型
     */
    @Column(name = "bicycle_type")
    private String bicycleType;

    /**
     * 故障类型
     */
    @Column(name = "fault_type")
    private String faultType;

    /**
     * 故障描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 预约时间
     */
    @Column(name = "appointment_time")
    private LocalDateTime appointmentTime;

    /**
     * 维修员ID（外键）
     */
    @Column(name = "repairman_id")
    private Long repairmanId;

    /**
     * 状态：0-待受理，1-维修中，2-已完成，3-已取消
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    // 状态常量定义
    public static final Integer STATUS_PENDING = 0;      // 待受理
    public static final Integer STATUS_IN_REPAIR = 1;    // 维修中
    public static final Integer STATUS_COMPLETED = 2;    // 已完成
    public static final Integer STATUS_CANCELED = 3;     // 已取消
}