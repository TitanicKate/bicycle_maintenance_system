package com.titanic.bicycle_maintenance_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titanic.bicycle_maintenance_system.pojo.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
