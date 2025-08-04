package com.titanic.bicycle_maintenance_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.titanic.bicycle_maintenance_system.pojo.dto.UserDTO;
import com.titanic.bicycle_maintenance_system.pojo.entity.User;

public interface UserService extends IService<User> {
    User login(UserDTO userDTO);

}
