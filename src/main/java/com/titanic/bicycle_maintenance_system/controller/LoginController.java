package com.titanic.bicycle_maintenance_system.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.titanic.bicycle_maintenance_system.pojo.dto.UserDTO;
import com.titanic.bicycle_maintenance_system.pojo.entity.Result;
import com.titanic.bicycle_maintenance_system.pojo.entity.User;
import com.titanic.bicycle_maintenance_system.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bms/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录接口，验证用户名密码并返回登录状态")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "登录成功",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "401", description = "登录失败，用户名或密码错误",
                    content = @Content(schema = @Schema(implementation = Result.class)))
    })
    public SaResult login(
            @Parameter(description = "登录信息，包含用户名和密码", required = true)
            @RequestBody UserDTO userDTO) {
        User user = userService.login(userDTO);
        if (user == null) {
            return SaResult.error("用户名或密码错误");
        }

        StpUtil.login(user.getId());

        return SaResult.ok("登录成功")
                .set("token", StpUtil.getTokenInfo())
                .set("role", user.getRole())
                .set("username", user.getUsername());
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "用户注册接口，创建一个新的用户")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "注册成功",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "400", description = "注册失败，用户名已存在",
                    content = @Content(schema = @Schema(implementation = Result.class)))
    })
    public SaResult register(
            @Parameter(description = "注册信息，包含用户名和密码", required = true)
            @RequestBody UserDTO userDTO) {
        boolean register = userService.register(userDTO);
        if (!register) {
            return SaResult.error("用户名已存在");
        }
        return SaResult.ok("注册成功");
    }
}
