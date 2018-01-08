package com.lss.user.config;

import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author liushuaishuai
 * @date 2018/1/8 下午2:23
 *
 * Mybatis, Mybatis Plus Configuration
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan("com.lss.user.mapper*")
public class MybatisConfig {


    @Bean
    public PerformanceInterceptor performanceInterceptor(){
        return new PerformanceInterceptor();
    }
}
