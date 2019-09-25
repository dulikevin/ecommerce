package com.duli.ecommercebackground;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

@Import(FdfsClientConfig.class)
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDubbo
public class EcommerceBackgroundApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcommerceBackgroundApplication.class, args);
    }
}
