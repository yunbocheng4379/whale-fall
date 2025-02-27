package com.sea.whale;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.sea.whale.mapper")
@EnableTransactionManagement
@EnableAsync
@EnableCaching
@EnableScheduling
public class WhaleFallApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhaleFallApplication.class, args);
    }

}
