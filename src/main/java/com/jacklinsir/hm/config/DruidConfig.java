package com.jacklinsir.hm.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author linSir
 * @version V1.0
 * @Description: (数据库连接词配置)
 * @Date 2019/12/30 22:21
 */
@Configuration
public class DruidConfig {


    /**
     * 配置初始化Druid连接池
     *
     * @return
     */
    @Bean(initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource getDataSource() {
        return new DruidDataSource();
    }
}
