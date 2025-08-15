package com.titanic.bicycle_maintenance_system.service;

import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.titanic.bicycle_maintenance_system.pojo.dto.UserDTO;
import com.titanic.bicycle_maintenance_system.pojo.entity.Result;
import com.titanic.bicycle_maintenance_system.pojo.entity.User;
import com.titanic.bicycle_maintenance_system.pojo.entity.UserPwdChange;
import com.titanic.bicycle_maintenance_system.pojo.vo.UserVO;

public interface UserService extends IService<User> {
    SaResult login(UserDTO userDTO);

    boolean register(UserDTO userDTO);

    Result<String> updatePassword(UserPwdChange userPwdChange);

    Result<UserVO> customUpdate(User user);
}
