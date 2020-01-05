package com.jacklinsir.hm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目启动类
 *
 * @author linSir
 */
@MapperScan(value = "com.jacklinsir.hm.dao")
@SpringBootApplication
public class HmApplication {
    public static void main(String[] args) {
        SpringApplication.run(HmApplication.class, args);
    }


}
