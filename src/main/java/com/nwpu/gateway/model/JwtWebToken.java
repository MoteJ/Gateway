package com.nwpu.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

/**
 * @Author: Mote
 * @Date: 2022/1/17 13:41
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtWebToken {
    private String accessToken;
    private String refreshToken;
}
