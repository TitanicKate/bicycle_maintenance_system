package com.titanic.bicycle_maintenance_system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.titanic.bicycle_maintenance_system.mapper.UserMapper;
import com.titanic.bicycle_maintenance_system.pojo.dto.UserDTO;
import com.titanic.bicycle_maintenance_system.pojo.entity.Result;
import com.titanic.bicycle_maintenance_system.pojo.entity.User;
import com.titanic.bicycle_maintenance_system.pojo.entity.UserPwdChange;
import com.titanic.bicycle_maintenance_system.pojo.vo.UserVO;
import com.titanic.bicycle_maintenance_system.service.UserService;
import com.titanic.bicycle_maintenance_system.utils.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private OSSUtil ossUtil;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean register(UserDTO userDTO) {
        // 先检查用户名是否已存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        if (count(queryWrapper) > 0) {
            return false;
        }

        String frontendEncryptedPassword = userDTO.getPassword();
        String backendEncryptedPassword = passwordEncoder.encode(frontendEncryptedPassword);

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(backendEncryptedPassword);
        user.setCreateTime(LocalDateTime.now());
        user.setRole(User.ROLE_USER);
        user.setStatus(User.STATUS_NORMAL);
        return save(user);
    }

    @Override
    public SaResult login(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        User user = getOne(queryWrapper);
        // 前端已加密，直接用前端加密后的密码和数据库中加密后的密码匹配
        if (user == null) {
            return SaResult.error("用户不存在");
        }

        String frontendEncryptedPassword = userDTO.getPassword();
        String backendEncryptedPassword = user.getPassword();

        if (!passwordEncoder.matches(frontendEncryptedPassword, backendEncryptedPassword)) {
            return SaResult.error("密码错误");
        }


        // 5. 可选：检查用户状态（如是否禁用）
        if (user.getStatus().equals(User.STATUS_DISABLED)) {
            return SaResult.error("当前用户被禁用");
        }

        StpUtil.login(user.getId());

        UserVO userVO = new UserVO();
        userVO.setId(String.valueOf(user.getId()));
        BeanUtil.copyProperties(user, userVO);

        return SaResult.ok("登录成功")
                .setData(userVO)
                .set("token", StpUtil.getTokenInfo());
    }

    @Override
    public Result<String> updatePassword(UserPwdChange userPwdChange) {
        User userById = getById(userPwdChange.getId());

        if (!passwordEncoder.matches(userPwdChange.getOldPassword(), userById.getPassword())) {
            return Result.error("旧密码错误");
        }

        User user = new User();
        user.setId(Long.valueOf(userPwdChange.getId()));
        user.setPassword(passwordEncoder.encode(userPwdChange.getNewPassword()));
        boolean updated = updateById(user);
        return updated ? Result.success("修改成功") : Result.error("修改失败");
    }

    @Override
    public Result<UserVO> customUpdate(User user) {
        User userById = getById(user.getId());
        String newAvatar = user.getAvatar();
        if (newAvatar != null) {
            String oldAvatar = userById.getAvatar();
            ossUtil.deleteFile(oldAvatar);
            userById.setAvatar(newAvatar);
        }
        boolean updated = updateById(user);
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(userById, userVO);
        return updated ? Result.success(userVO, "更新成功") : Result.error("更新失败");
    }
}