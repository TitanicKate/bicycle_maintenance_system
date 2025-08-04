package com.titanic.bicycle_maintenance_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.titanic.bicycle_maintenance_system.mapper.UserMapper;
import com.titanic.bicycle_maintenance_system.pojo.dto.UserDTO;
import com.titanic.bicycle_maintenance_system.pojo.entity.User;
import com.titanic.bicycle_maintenance_system.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public User login(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername())
                .eq("password", userDTO.getPassword());

        return getOne(queryWrapper);
    }
}
