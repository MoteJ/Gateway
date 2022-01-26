package com.nwpu.gateway.helper;

public class RedisKeyHelper {
    public static String getUserRedisKey(String userId) {
        return "user:" + userId.toLowerCase();
    }
}
