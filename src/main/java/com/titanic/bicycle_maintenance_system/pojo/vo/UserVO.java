package com.titanic.bicycle_maintenance_system.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVO {
    // 主键ID（用于标识用户）
    private Long id;

    // 用户名（前端展示或登录相关）
    private String username;

    // 真实姓名（用户个人信息展示）
    private String name;

    // 角色（通常需转换为角色名称，如"管理员"而非1，也可保留数字用于权限判断）
    private Integer role;
    // 可选：角色名称（便于前端直接展示，无需前端转换）
    private String roleName;

    // 手机号（用户联系方式展示）
    private String phone;

    // 创建时间（用户信息相关的时间展示）
    private LocalDateTime createTime;

    // 状态（通常需转换为状态名称，如"正常"而非1，也可保留数字）
    private Integer status;
    // 可选：状态名称（便于前端直接展示）
}
