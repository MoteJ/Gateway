package com.nwpu.gateway.infrastructure.repository;

import com.nwpu.gateway.infrastructure.redis.RedisRepository;
import com.nwpu.gateway.model.JwtWebToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @Author: Mote
 * @Date: 2022/1/17 15:05
 */
@Repository
@AllArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {
    private RedisRepository redisRepository;
    @Override
    public boolean save(String ccid, JwtWebToken jwtWebToken, int expireTime) {
        try {
            redisRepository.set(ccid + "a", jwtWebToken.getAccessToken());
            redisRepository.set(ccid + "r", jwtWebToken.getRefreshToken());
            redisRepository.expire(ccid + "a", expireTime);
            redisRepository.expire(ccid + "r", expireTime);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String getAccessToken(String ccid) {
        return redisRepository.get(ccid + "a");
    }

    @Override
    public String getRefreshToken(String ccid) {
        return redisRepository.get(ccid + "r");
    }
}
