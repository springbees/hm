package com.jacklinsir.hm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * 项目启动类
 *
 * @author linSir
 */
@MapperScan(value = "com.jacklinsir.hm.dao")
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class HmApplication {
    public static void main(String[] args) {
        SpringApplication.run(HmApplication.class, args);
    }


}
