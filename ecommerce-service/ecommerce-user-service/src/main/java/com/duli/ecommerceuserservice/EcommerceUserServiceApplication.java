package com.duli.ecommerceuserservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.duli.mapper")
public class EcommerceUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceUserServiceApplication.class, args);
    }

}
