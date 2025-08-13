package com.titanic.bicycle_maintenance_system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.titanic.bicycle_maintenance_system.mapper.AnnouncementMapper;
import com.titanic.bicycle_maintenance_system.pojo.entity.Announcement;
import com.titanic.bicycle_maintenance_system.service.AnnouncementService;
import org.springframework.stereotype.Service;

@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement> implements AnnouncementService {
}
