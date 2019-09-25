package com.duli.ecommerceindex;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class EcommerceIndexApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceIndexApplication.class, args);
    }

}
