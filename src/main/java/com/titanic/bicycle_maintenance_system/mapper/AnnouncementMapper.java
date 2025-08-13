package com.titanic.bicycle_maintenance_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titanic.bicycle_maintenance_system.pojo.entity.Announcement;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AnnouncementMapper extends BaseMapper<Announcement> {
}
