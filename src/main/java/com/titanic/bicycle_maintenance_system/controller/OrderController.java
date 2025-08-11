package com.titanic.bicycle_maintenance_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.titanic.bicycle_maintenance_system.pojo.entity.Order;
import com.titanic.bicycle_maintenance_system.pojo.entity.Result;
import com.titanic.bicycle_maintenance_system.pojo.vo.OrderVO;
import com.titanic.bicycle_maintenance_system.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/bms/orders")
@Tag(name = "订单管理", description = "订单的创建、查询、更新、删除等操作接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    @Operation(summary = "新增订单", description = "创建新的订单信息，返回新增结果")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "新增成功",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "500", description = "新增失败",
                    content = @Content(schema = @Schema(implementation = Result.class)))
    })
    public Result<Order> addOrder(
            @Parameter(description = "订单信息对象，包含用户ID、自行车类型等信息", required = true)
            @RequestBody Order order
    ) {
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        order.setStatus(Order.STATUS_PENDING); // 默认为待受理状态
        boolean saved = orderService.save(order);
        return saved ? Result.success(order, "新增成功") : Result.error("新增失败");
    }

    // 根据 ID 查询
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询订单", description = "通过订单ID获取订单详细信息")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "404", description = "订单不存在",
                    content = @Content(schema = @Schema(implementation = Result.class)))
    })
    public Result<Order> getOrderById(
            @Parameter(description = "订单ID", required = true, example = "1")
            @PathVariable Long id) {
        Order order = orderService.getById(id);
        return Result.success(order);
    }

    // 条件查询
    @GetMapping("/list")
    @Operation(summary = "条件查询订单列表", description = "根据用户ID或状态查询订单列表，不填则返回所有订单")
    @ApiResponse(responseCode = "200", description = "查询成功",
            content = @Content(schema = @Schema(implementation = Result.class)))
    public Result<List<OrderVO>> getOrderList(
            @Parameter(description = "用户ID（可选）", example = "1")
            @RequestParam(required = false) Long userId,
            @Parameter(description = "订单状态（可选），0-待受理，1-维修中，2-已完成，3-已取消", example = "0")
            @RequestParam(required = false) Integer status) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        if (userId != null) {
            queryWrapper.eq("user_id", userId);
        }
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        List<Order> list = orderService.list(queryWrapper);
        List<OrderVO> orderVos = list.stream().map(order -> {
            OrderVO orderVO = new OrderVO();
            orderVO.setId(String.valueOf(order.getId()));
            BeanUtils.copyProperties(order, orderVO);
            return orderVO;
        }).toList();
        return Result.success(orderVos);
    }

    // 分页查询
    @GetMapping("/page")
    @Operation(summary = "分页查询订单", description = "分页获取订单列表，支持指定页码和每页条数")
    @ApiResponse(responseCode = "200", description = "查询成功",
            content = @Content(schema = @Schema(implementation = Result.class)))
    public Result<IPage<Order>> getOrderPage(
            @Parameter(description = "页码，默认1", example = "1")
            @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页条数，默认10", example = "10")
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Order> page = new Page<>(pageNum, pageSize);
        IPage<Order> orderPage = orderService.page(page);
        return Result.success(orderPage);
    }

    // 更新订单
    @PutMapping
    @Operation(summary = "更新订单信息", description = "根据订单ID更新订单信息，返回更新结果")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "更新成功",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "500", description = "更新失败",
                    content = @Content(schema = @Schema(implementation = Result.class)))
    })
    public Result<Order> updateOrder(
            @Parameter(description = "订单信息对象（需包含ID）", required = true)
            @RequestBody Order order) {
        order.setUpdateTime(LocalDateTime.now()); // 更新时间戳
        boolean updated = orderService.updateById(order);
        return updated ? Result.success(order, "更新成功") : Result.error("更新失败");
    }

    // 删除订单（逻辑删除）
    @DeleteMapping("/{id}")
    @Operation(summary = "删除订单", description = "根据订单ID逻辑删除订单，返回删除结果")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "删除成功",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "500", description = "删除失败",
                    content = @Content(schema = @Schema(implementation = Result.class)))
    })
    public Result<Void> deleteOrder(
            @Parameter(description = "订单ID", required = true, example = "1")
            @PathVariable Long id) {
        boolean deleted = orderService.removeById(id);
        return deleted ? Result.success("删除成功") : Result.error("删除失败");
    }

    // 批量删除
    @DeleteMapping("/batch")
    @Operation(summary = "批量删除订单", description = "根据订单ID列表批量逻辑删除订单，返回删除结果")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "删除成功",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "500", description = "删除失败",
                    content = @Content(schema = @Schema(implementation = Result.class)))
    })
    public Result<Void> deleteOrders(
            @Parameter(description = "订单ID列表", required = true)
            @RequestBody List<Long> ids) {
        boolean deleted = orderService.removeByIds(ids);
        return deleted ? Result.success("删除成功") : Result.error("删除失败");
    }
}