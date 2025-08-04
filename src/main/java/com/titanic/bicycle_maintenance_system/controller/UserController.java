package com.titanic.bicycle_maintenance_system.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bms/user")
@Tag(name = "用户管理", description = "用户的创建、查询、更新、删除及登录等操作接口")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    @Operation(summary = "新增用户", description = "创建新的用户信息，返回新增结果")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "新增成功",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "500", description = "新增失败",
                    content = @Content(schema = @Schema(implementation = Result.class)))
    })
    public Result<User> addUser(
            @Parameter(description = "用户信息对象，包含用户名、密码、角色等信息", required = true)
            @RequestBody User user) {
        boolean saved = userService.save(user);
        return saved ? Result.success(user, "新增成功") : Result.error("新增失败");
    }

    // 根据 ID 查询
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询用户", description = "通过用户ID获取用户详细信息")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "404", description = "用户不存在",
                    content = @Content(schema = @Schema(implementation = Result.class)))
    })
    public Result<User> getUserById(
            @Parameter(description = "用户ID", required = true, example = "1")
            @PathVariable Long id) {
        User user = userService.getById(id);
        return Result.success(user);
    }

    // 条件查询
    @GetMapping("/list")
    @Operation(summary = "条件查询用户列表", description = "根据角色ID查询用户列表，不填角色则返回所有用户")
    @ApiResponse(responseCode = "200", description = "查询成功",
            content = @Content(schema = @Schema(implementation = Result.class)))
    public Result<List<User>> getUserList(
            @Parameter(description = "角色ID（可选），1-管理员，2-普通用户", example = "1")
            @RequestParam(required = false) Integer role) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (role != null) {
            queryWrapper.eq("role", role);
        }
        List<User> list = userService.list(queryWrapper);
        return Result.success(list);
    }

    // 分页查询
    @GetMapping("/page")
    @Operation(summary = "分页查询用户", description = "分页获取用户列表，支持指定页码和每页条数")
    @ApiResponse(responseCode = "200", description = "查询成功",
            content = @Content(schema = @Schema(implementation = Result.class)))
    public Result<IPage<User>> getUserPage(
            @Parameter(description = "页码，默认1", example = "1")
            @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页条数，默认10", example = "10")
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);
        IPage<User> userPage = userService.page(page);
        return Result.success(userPage);
    }

    // 更新用户
    @PutMapping
    @Operation(summary = "更新用户信息", description = "根据用户ID更新用户信息，返回更新结果")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "更新成功",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "500", description = "更新失败",
                    content = @Content(schema = @Schema(implementation = Result.class)))
    })
    public Result<User> updateUser(
            @Parameter(description = "用户信息对象（需包含ID）", required = true)
            @RequestBody User user) {
        boolean updated = userService.updateById(user);
        return updated ? Result.success(user, "更新成功") : Result.error("更新失败");
    }

    // 删除用户（逻辑删除）
    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户", description = "根据用户ID逻辑删除用户，返回删除结果")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "删除成功",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "500", description = "删除失败",
                    content = @Content(schema = @Schema(implementation = Result.class)))
    })
    public Result<Void> deleteUser(
            @Parameter(description = "用户ID", required = true, example = "1")
            @PathVariable Long id) {
        boolean deleted = userService.removeById(id);
        return deleted ? Result.success("删除成功") : Result.error("删除失败");
    }

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


        return SaResult.ok()
                .set("token", StpUtil.getTokenInfo())
                .set("userId", user.getId())
                .set("username", user.getUsername());
    }
}