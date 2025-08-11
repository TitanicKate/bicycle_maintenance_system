package com.titanic.bicycle_maintenance_system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 订单实体类
 * 对应数据库中的order表
 */
@Data
@Table(name = "t_order")
@TableName("t_order")
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
    private Integer bicycleType;

    /**
     * 故障类型
     */
    @Column(name = "fault_type")
    private Integer faultType;

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

    /**
     * 是否需要上门维修服务
     */
    @Column(name = "need_service")
    private Integer needService;

    /**
     * 上门维修服务地址
     */
    @Column(name = "service_address")
    private String serviceAddress;

    // 状态常量定义
    public static final Integer STATUS_PENDING = 0;      // 待受理
    public static final Integer STATUS_IN_REPAIR = 1;    // 维修中
    public static final Integer STATUS_COMPLETED = 2;    // 已完成
    public static final Integer STATUS_CANCELED = 3;     // 已取消

    // 定义故障类型常量
    public static final Integer FAULT_TYPE_OTHER = 0;
    public static final Integer FAULT_TYPE_BROKEN_TIRE = 1;
    public static final Integer FAULT_TYPE_BRAKE_FAILURE = 2;
    public static final Integer FAULT_TYPE_CHAIN_FAILURE = 3;
    public static final Integer FAULT_TYPE_WHEEL_FAILURE = 4;

    // 定义自行车类型常量
    public static final Integer BICYCLE_TYPE_COMMON = 0;
    public static final Integer BICYCLE_TYPE_MOUNTAIN = 1;
    public static final Integer BICYCLE_TYPE_ROAD = 2;
}