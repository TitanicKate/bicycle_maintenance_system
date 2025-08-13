package com.titanic.bicycle_maintenance_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.titanic.bicycle_maintenance_system.pojo.entity.Announcement;
import com.titanic.bicycle_maintenance_system.pojo.entity.Result;
import com.titanic.bicycle_maintenance_system.pojo.vo.AnnouncementVO;
import com.titanic.bicycle_maintenance_system.service.AnnouncementService;
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
@RequestMapping("/bms/announcements")
@Tag(name = "公告管理", description = "公告的创建、查询、更新、删除操作接口")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @PostMapping("/add")
    @Operation(summary = "新增公告", description = "创建新的公告信息，返回新增结果")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "新增成功",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "500", description = "新增失败",
                    content = @Content(schema = @Schema(implementation = Result.class)))
    })
    public Result<Announcement> addAnnouncement(
            @Parameter(description = "公告信息对象，包含标题、内容等信息", required = true)
            @RequestBody Announcement announcement
    ) {
        announcement.setCreateTime(LocalDateTime.now());
        announcement.setUpdateTime(LocalDateTime.now());
        boolean saved = announcementService.save(announcement);
        return saved ? Result.success(announcement, "新增成功") : Result.error("新增失败");
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询公告", description = "通过公告ID获取公告详细信息")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "404", description = "公告不存在",
                    content = @Content(schema = @Schema(implementation = Result.class)))
    })
    public Result<Announcement> getAnnouncementById(
            @Parameter(description = "公告ID", required = true, example = "1")
            @PathVariable Long id) {
        Announcement announcement = announcementService.getById(id);
        return Result.success(announcement);
    }

    @GetMapping("/list")
    @Operation(summary = "条件查询公告列表", description = "根据公告状态查询公告列表，不填则返回所有公告")
    @ApiResponse(responseCode = "200", description = "查询成功",
            content = @Content(schema = @Schema(implementation = Result.class)))
    public Result<List<AnnouncementVO>> getAnnouncementList(
            @Parameter(description = "公告状态（可选），0-未发布，1-已发布，2-已下架", example = "1")
            @RequestParam(required = false) Integer status) {
        QueryWrapper<Announcement> queryWrapper = new QueryWrapper<>();
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        // 按创建时间降序排序，最新公告在前
        queryWrapper.orderByDesc("create_time");
        List<Announcement> list = announcementService.list(queryWrapper);
        List<AnnouncementVO> announcementVos = list.stream().map(announcement -> {
            AnnouncementVO announcementVO = new AnnouncementVO();
            announcementVO.setId(String.valueOf(announcement.getId()));
            BeanUtils.copyProperties(announcement, announcementVO);
            return announcementVO;
        }).toList();
        return Result.success(announcementVos);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询公告", description = "分页获取公告列表，支持指定页码和每页条数")
    @ApiResponse(responseCode = "200", description = "查询成功",
            content = @Content(schema = @Schema(implementation = Result.class)))
    public Result<IPage<Announcement>> getAnnouncementPage(
            @Parameter(description = "页码，默认1", example = "1")
            @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页条数，默认10", example = "10")
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Announcement> page = new Page<>(pageNum, pageSize);
        // 分页查询时按创建时间降序
        IPage<Announcement> announcementPage = announcementService.page(page,
                new QueryWrapper<Announcement>().orderByDesc("create_time"));
        return Result.success(announcementPage);
    }

    @PutMapping
    @Operation(summary = "更新公告信息", description = "根据公告ID更新公告信息，返回更新结果")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "更新成功",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "500", description = "更新失败",
                    content = @Content(schema = @Schema(implementation = Result.class)))
    })
    public Result<Announcement> updateAnnouncement(
            @Parameter(description = "公告信息对象（需包含ID）", required = true)
            @RequestBody Announcement announcement) {
        announcement.setUpdateTime(LocalDateTime.now());
        boolean updated = announcementService.updateById(announcement);
        return updated ? Result.success(announcement, "更新成功") : Result.error("更新失败");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除公告", description = "根据公告ID逻辑删除公告，返回删除结果")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "删除成功",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "500", description = "删除失败",
                    content = @Content(schema = @Schema(implementation = Result.class)))
    })
    public Result<Void> deleteAnnouncement(
            @Parameter(description = "公告ID", required = true, example = "1")
            @PathVariable Long id) {
        boolean deleted = announcementService.removeById(id);
        return deleted ? Result.success("删除成功") : Result.error("删除失败");
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除公告", description = "根据公告ID列表批量逻辑删除公告，返回删除结果")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "删除成功",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "500", description = "删除失败",
                    content = @Content(schema = @Schema(implementation = Result.class)))
    })
    public Result<Void> deleteAnnouncements(
            @Parameter(description = "公告ID列表", required = true)
            @RequestBody List<Long> ids) {
        boolean deleted = announcementService.removeByIds(ids);
        return deleted ? Result.success("删除成功") : Result.error("删除失败");
    }
}