package com.sea.whale.config;/**
 * @author : YunboCheng
 * @date : 18:14 2022-08-28
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;


//@EnableAsync
//@Configuration
public class ExecutePoolConfig {

    @Value(value = "${threadPool.corePoolSize}")
    private Integer corePoolSize;

    @Value(value = "${threadPool.maxPoolSize}")
    private Integer maxPoolSize;

    @Value(value = "${threadPool.queueCapacity}")
    private Integer queueCapacity;

    @Bean("thread")
    public TaskExecutor taskExecutor(){
        // 创建线程池
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置初始化线程池大小
        executor.setCorePoolSize(corePoolSize);
        // 设置最大线程池大小
        executor.setMaxPoolSize(maxPoolSize);
        // 设置阻塞队列大小
        executor.setQueueCapacity(queueCapacity);
        // 设置线程前缀
        executor.setThreadNamePrefix("thread-");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

}
