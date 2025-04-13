package com.xmall.user.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.thread")
@Data
public class ThreadPoolConfig {
    private Integer coreSize;
    private Integer maxSize;
    private Integer keepAliveTime;
}
