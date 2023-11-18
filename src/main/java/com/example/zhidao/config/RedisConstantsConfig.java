package com.example.zhidao.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "redis-constants")
public class RedisConstantsConfig {
    private String aiAnswerKey="aiAnswer:";

    public Long aiAnswerTTL;

}
