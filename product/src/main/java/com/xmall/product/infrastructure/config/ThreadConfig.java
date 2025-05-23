package com.xmall.product.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor(ThreadPoolConfig pool) {
        return new ThreadPoolExecutor(pool.getCoreSize(),
                                      pool.getMaxSize(),
                                      pool.getKeepAliveTime(),
                                      TimeUnit.SECONDS,
                                      new LinkedBlockingDeque<>(100000),
                                      Executors.defaultThreadFactory(),
                                      new ThreadPoolExecutor.AbortPolicy());
    }
}
