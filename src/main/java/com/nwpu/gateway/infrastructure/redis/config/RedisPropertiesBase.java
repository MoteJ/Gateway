package com.nwpu.gateway.infrastructure.redis.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Component
@Data
@NoArgsConstructor
public class RedisPropertiesBase {
    @NotBlank
    private String nodes;

    @NotBlank
    private String password;

    @NotBlank
    private Integer maxRedirects;

    @NotBlank
    @Min(1)
    private Integer maxActive;

    @NotBlank
    @Min(0)
    private Integer maxIdle;

    @NotBlank
    @Min(0)
    private Integer minIdle;

    @NotBlank
    @Min(0)
    private Integer maxWait;

    @NotBlank
    private Boolean blockWhenExhausted;

    @NotBlank
    private Boolean testOnBorrow;

    @NotBlank
    private Boolean testOnReturn;
}
