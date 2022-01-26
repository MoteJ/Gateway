package com.nwpu.gateway.infrastructure.redis;

import java.util.Date;
import java.util.Map;

public interface RedisRepository {
    Boolean hasKey(String redisKey);

    void set(String redisKey, String value);

    String get(String redisKey);

    void decrement(String redisKey);

    void increment(String redisKey);

    Boolean hashExist(String redisKey, String hashField);

    void hashPut(String redisKey, String hashField, Object value);

    void hashPutAll(String redisKey, Map<String, String> values);

    String hashGet(String redisKey, String hashField);

    Map<String, String> hashGetAll(String redisKey);

    void hashDecrement(String redisKey, String hashField);

    void hashIncrement(String redisKey, String hashField);

    void expireAt(String redisKey, Date date);

    void expire(String redisKey, long timeout);

    void delete(String redisKey);

    public void sortedSetAdd(String key, String value, double score);
}
