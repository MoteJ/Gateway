package com.nwpu.gateway.infrastructure.redis.config;

import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.redis.clusters.bak")
@NoArgsConstructor
public class RedisPropertiesBak extends RedisPropertiesBase {
}
