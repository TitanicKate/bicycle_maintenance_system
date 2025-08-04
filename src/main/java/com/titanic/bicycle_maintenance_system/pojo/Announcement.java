package com.titanic.bicycle_maintenance_system.pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 公告/文章实体类
 */
@Data
@Table(name = "t_announcement")
@Entity
public class Announcement {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * 发布人ID（外键，关联用户表）
     */
    @Column(name = "author_id")
    private Long authorId;

    /**
     * 是否置顶：0-否，1-是
     */
    @Column(name = "is_top")
    private Integer isTop;

    // 是否置顶常量定义
    public static final Integer IS_TOP_NO = 0;    // 否
    public static final Integer IS_TOP_YES = 1;   // 是
}