package com.nwpu.gateway.infrastructure.repository;

import com.nwpu.gateway.model.JwtWebToken;

/**
 * @Author: Mote
 * @Date: 2022/1/17 13:12
 */
public interface TokenRepository {
    /**
     *
     * @param ccid
     * @param jwtWebToken
     * @param expireTime
     * @return
     */
    boolean save(String ccid, JwtWebToken jwtWebToken, int expireTime);

    String getAccessToken(String ccid);

    String getRefreshToken(String ccid);
}
