package com.tboostAI_core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "google.api")
@Getter
@Setter
public class GoogleApiConfigProperties {
    private String baseUrl;
    private String key;
}