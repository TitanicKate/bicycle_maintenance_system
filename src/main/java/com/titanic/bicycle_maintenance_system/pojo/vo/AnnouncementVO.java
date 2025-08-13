package com.titanic.bicycle_maintenance_system.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnnouncementVO {
    private String id;
    private String title;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long authorId;
    private Integer isTop;
}
