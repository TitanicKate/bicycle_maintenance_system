package com.titanic.bicycle_maintenance_system.pojo.dto;

import lombok.Data;

/**
 * 用户实体类（对应数据库表）
 */
@Data
public class UserDTO {
    private String username; // 用户名
    private String password; // 密码（实际项目中需加密存储）
}
