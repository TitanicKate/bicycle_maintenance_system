package com.titanic.bicycle_maintenance_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. 配置接口访问规则
                .authorizeHttpRequests(auth -> auth
                        // 放行登录、注册接口（根据你的实际接口路径修改）
//                        .requestMatchers("/login", "/register").permitAll()
                        // 放行静态资源（如前端页面、JS/CSS等）
//                        .requestMatchers("/static/**", "/index.html").permitAll()
                        // 其他所有接口需要认证（登录后才能访问）
                        .anyRequest().permitAll()
                )
                // 2. 关闭 CSRF 保护（开发环境临时关闭，避免因 Token 缺失导致 403）
                .csrf(csrf -> csrf.disable())
                // 3. 关闭默认的表单登录（如果用自己的登录接口）
                .formLogin(form -> form.disable())
                // 4. 关闭 HTTP 基本认证（避免弹出浏览器默认登录框）
                .httpBasic(httpBasic -> httpBasic.disable());
        return http.build();
    }
}
