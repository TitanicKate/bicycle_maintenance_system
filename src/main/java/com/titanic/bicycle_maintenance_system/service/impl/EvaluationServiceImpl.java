package com.titanic.bicycle_maintenance_system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.titanic.bicycle_maintenance_system.mapper.EvaluationMapper;
import com.titanic.bicycle_maintenance_system.pojo.entity.Evaluation;
import com.titanic.bicycle_maintenance_system.service.EvaluationService;
import org.springframework.stereotype.Service;

@Service
public class EvaluationServiceImpl extends ServiceImpl<EvaluationMapper, Evaluation> implements EvaluationService {
}
