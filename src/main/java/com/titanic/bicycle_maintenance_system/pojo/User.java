package com.titanic.bicycle_maintenance_system.pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体类
 * 对应数据库中的user表
 */
@Data
@Table(name = "t_user")
@Entity
public class User {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 密码（加密存储）
     */
    @Column(name = "password")
    private String password;

    /**
     * 真实姓名
     */
    @Column(name = "name")
    private String name;

    /**
     * 角色：1-管理员，2-维修员，3-学生
     */
    @Column(name = "role")
    private Integer role;

    /**
     * 手机号
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * 状态：0-禁用，1-正常
     */
    @Column(name = "status")
    private Integer status;

    // 角色常量定义
    public static final Integer ROLE_ADMIN = 1;       // 管理员
    public static final Integer ROLE_REPAIRMAN = 2;   // 维修员
    public static final Integer ROLE_STUDENT = 3;     // 学生

    // 状态常量定义
    public static final Integer STATUS_DISABLED = 0;  // 禁用
    public static final Integer STATUS_NORMAL = 1;    // 正常
}
