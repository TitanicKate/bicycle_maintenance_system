package com.titanic.bicycle_maintenance_system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.titanic.bicycle_maintenance_system.mapper")
public class BicycleMaintenanceSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BicycleMaintenanceSystemApplication.class, args);
    }

}
