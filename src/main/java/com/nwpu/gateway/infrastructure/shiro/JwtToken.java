package com.nwpu.gateway.infrastructure.shiro;

import lombok.Builder;
import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author: Mote
 * @Date: 2022/1/16 15:42
 */
@Data
@Builder
public class JwtToken implements AuthenticationToken {
    private String token;

    public JwtToken() {

    }
    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
