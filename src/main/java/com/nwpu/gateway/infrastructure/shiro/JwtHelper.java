package com.nwpu.gateway.infrastructure.shiro;

import com.nwpu.gateway.infrastructure.repository.TokenRepository;
import com.nwpu.gateway.model.AuthorizedUserInfo;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author: Mote
 * @Date: 2022/1/16 15:33
 */
public interface JwtHelper {

    String create(AuthorizedUserInfo userInfo);

    AuthorizedUserInfo verify(String token);

    String refresh(TokenContent claims);

    TokenContent getTokenContent(Claims claims);
}
