package com.nwpu.gateway.infrastructure.shiro;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: Mote
 * @Date: 2022/1/17 11:43
 */
@Component
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private Integer accessExpireSeconds;
    private Integer refreshExpireSeconds;
    private String secret;
    private String refreshSecret;
}
