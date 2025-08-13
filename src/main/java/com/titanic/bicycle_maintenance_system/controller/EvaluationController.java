package com.titanic.bicycle_maintenance_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.titanic.bicycle_maintenance_system.pojo.entity.Evaluation;
import com.titanic.bicycle_maintenance_system.pojo.entity.Result;
import com.titanic.bicycle_maintenance_system.pojo.vo.EvaluationVO;
import com.titanic.bicycle_maintenance_system.service.EvaluationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bms/evaluations")
@Tag(name = "评价管理", description = "评价的创建、查询、更新、删除操作接口")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    // 条件查询
    @GetMapping("/list")
    @Operation(summary = "条件查询评价列表", description = "根据相关条件查询评价列表，不填条件则返回所有评价")
    @ApiResponse(responseCode = "200", description = "查询成功",
            content = @Content(schema = @Schema(implementation = Result.class)))
    public Result<List<EvaluationVO>> getEvaluationList(
            @Parameter(description = "维修单ID（可选），用于查询特定维修单的评价", example = "1")
            @RequestParam(required = false) Long repairOrderId,
            @Parameter(description = "评价等级（可选），1-5星", example = "5")
            @RequestParam(required = false) Integer rating) {
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<>();
        if (repairOrderId != null) {
            queryWrapper.eq("repair_order_id", repairOrderId);
        }
        if (rating != null) {
            queryWrapper.eq("rating", rating);
        }
        List<Evaluation> list = evaluationService.list(queryWrapper);
        List<EvaluationVO> evaluationVos = list.stream().map(evaluation -> {
            EvaluationVO evaluationVO = new EvaluationVO();
            evaluationVO.setId(String.valueOf(evaluation.getId()));
            BeanUtils.copyProperties(evaluation, evaluationVO);
            return evaluationVO;
        }).collect(Collectors.toList());
        return Result.success(evaluationVos);
    }

    // 分页查询
    @GetMapping("/page")
    @Operation(summary = "分页查询评价", description = "分页获取评价列表，支持指定页码和每页条数")
    @ApiResponse(responseCode = "200", description = "查询成功",
            content = @Content(schema = @Schema(implementation = Result.class)))
    public Result<IPage<Evaluation>> getEvaluationPage(
            @Parameter(description = "页码，默认1", example = "1")
            @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页条数，默认10", example = "10")
            @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "维修单ID（可选），用于分页查询特定维修单的评价", example = "1")
            @RequestParam(required = false) Long repairOrderId) {
        Page<Evaluation> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<>();
        if (repairOrderId != null) {
            queryWrapper.eq("repair_order_id", repairOrderId);
        }
        IPage<Evaluation> evaluationPage = evaluationService.page(page, queryWrapper);
        return Result.success(evaluationPage);
    }
}