package com.example.zhidao.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "xf-rest")
@Data
public class XfRestConfig {
    private String hostUrl;
    private String sendQuestionUri;
}
