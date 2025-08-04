package com.titanic.bicycle_maintenance_system.pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 维修记录实体类
 */
@Data
@Table(name = "t_repair_record")
@Entity
public class RepairRecord {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 订单ID（外键）
     */
    @Column(name = "order_id")
    private Long orderId;

    /**
     * 维修内容
     */
    @Column(name = "repair_content")
    private String repairContent;

    /**
     * 使用配件
     */
    @Column(name = "parts_used")
    private String partsUsed;

    /**
     * 维修费用
     */
    @Column(name = "cost")
    private BigDecimal cost;

    /**
     * 维修时间
     */
    @Column(name = "repair_time")
    private LocalDateTime repairTime;

    /**
     * 维修员ID（外键）
     */
    @Column(name = "repairman_id")
    private Long repairmanId;
}