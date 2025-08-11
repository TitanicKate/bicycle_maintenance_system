package com.titanic.bicycle_maintenance_system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.titanic.bicycle_maintenance_system.mapper.OrderMapper;
import com.titanic.bicycle_maintenance_system.pojo.entity.Order;
import com.titanic.bicycle_maintenance_system.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
}
