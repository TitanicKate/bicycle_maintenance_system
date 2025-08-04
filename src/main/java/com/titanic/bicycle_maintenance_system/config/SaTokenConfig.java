package com.titanic.bicycle_maintenance_system.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-Token 配置类
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    /**
     * 注册 Sa-Token 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器
        registry.addInterceptor(new SaInterceptor(handle -> {
                    // 拦截规则：除了登录接口外，其他接口都需要登录验证
                    StpUtil.checkLogin(); // 未登录会抛出 NotLoginException 异常
                }))
                .addPathPatterns("/**")                    // 拦截所有接口
                .excludePathPatterns(
                        "/bms/user/login",
                        // Swagger UI页面路径
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        // OpenAPI文档接口路径
                        "/v3/api-docs/**"
                );   // 排除登录接口
    }
}