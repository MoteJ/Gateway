package com.nwpu.gateway.infrastructure.shiro;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Mote
 * @Date: 2022/1/16 19:54
 */
@Data
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "login")
public class LoginProperties {
    private List<String> passUrls;
}
