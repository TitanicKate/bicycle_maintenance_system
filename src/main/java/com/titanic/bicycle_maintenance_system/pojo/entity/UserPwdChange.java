package com.titanic.bicycle_maintenance_system.pojo.entity;

import lombok.Data;

@Data
public class UserPwdChange {
    private String id;
    private String oldPassword;
    private String newPassword;
}
