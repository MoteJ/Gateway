package com.nwpu.gateway.infrastructure.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.SECONDS;

@Repository
public class RedisRepositoryImpl implements RedisRepository {

    @Autowired
    @Qualifier("clusters-master")
    private StringRedisTemplate redisTemplate;

    @Autowired
    @Qualifier("clusters-bak")
    private StringRedisTemplate redisTemplateBak;

    @Override
    public Boolean hasKey(String redisKey) {
        try {
            return redisTemplate.hasKey(redisKey);
        } catch (Exception e) {
            e.printStackTrace();
            return redisTemplateBak.hasKey(redisKey);
        }
    }

    @Override
    public void set(String redisKey, String value) {
        try {
            redisTemplate.opsForValue().set(redisKey, value);
        } catch (Exception e) {
            redisTemplateBak.opsForValue().set(redisKey, value);
        }
    }

    @Override
    public String get(String redisKey) {
        try{
            return redisTemplate.opsForValue().get(redisKey);
        } catch (Exception e) {
            return redisTemplateBak.opsForValue().get(redisKey);
        }
    }

    @Override
    public void decrement(String redisKey) {
        try {
            redisTemplate.opsForValue().decrement(redisKey);
        } catch (Exception e) {
            redisTemplateBak.opsForValue().decrement(redisKey);
        }
    }

    @Override
    public void increment(String redisKey) {
        try {
            redisTemplate.opsForValue().increment(redisKey);
        } catch (Exception e) {
            redisTemplateBak.opsForValue().increment(redisKey);
        }
    }

    @Override
    public Boolean hashExist(String redisKey, String hashField) {
        try {
            return redisTemplate.opsForHash().hasKey(redisKey, hashField);
        } catch (Exception e) {
            return redisTemplateBak.opsForHash().hasKey(redisKey, hashField);
        }
    }

    @Override
    public void hashPut(String redisKey, String hashField, Object value) {
        try {
            redisTemplate.opsForHash().put(redisKey, hashField, value);
        } catch (Exception e) {
            redisTemplateBak.opsForHash().put(redisKey, hashField, value);
        }
    }

    @Override
    public void hashPutAll(String redisKey, Map<String, String> values) {
        try {
            redisTemplate.opsForHash().putAll(redisKey, values);
        } catch (Exception e) {
            redisTemplateBak.opsForHash().putAll(redisKey, values);
        }
    }

    @Override
    public String hashGet(String redisKey, String hashField) {
        Object value;
        try {
            value = redisTemplate.opsForHash().get(redisKey, hashField);
        } catch (Exception e) {
            value = redisTemplateBak.opsForHash().get(redisKey, hashField);
        }
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    @Override
    public Map<String, String> hashGetAll(String redisKey) {
        try {
            return redisTemplate.opsForHash().entries(redisKey)
                .entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey().toString(), entry -> entry.getValue().toString()));
        } catch (Exception ex) {
            return redisTemplateBak.opsForHash().entries(redisKey)
                .entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey().toString(), entry -> entry.getValue().toString()));
        }

    }

    @Override
    public void hashDecrement(String redisKey, String hashField) {
        try {
            redisTemplate.opsForHash().increment(redisKey, hashField, 1);
        } catch (Exception e) {
            redisTemplateBak.opsForHash().increment(redisKey, hashField, 1);
        }
    }

    @Override
    public void hashIncrement(String redisKey, String hashField) {
        try {
            redisTemplate.opsForHash().increment(redisKey, hashField, -1);
        } catch (Exception e) {
            redisTemplateBak.opsForHash().increment(redisKey, hashField, -1);
        }
    }

    @Override
    public void expireAt(String redisKey, Date date) {
        try {
            redisTemplate.expireAt(redisKey, date);
        } catch (Exception e) {
            redisTemplateBak.expireAt(redisKey, date);
        }
    }

    @Override
    public void expire(String redisKey, long timeout) {
        try {
            redisTemplate.expire(redisKey, timeout, SECONDS);
        } catch (Exception e) {
            redisTemplateBak.expire(redisKey, timeout, SECONDS);
        }
    }

    @Override
    public void delete(String redisKey) {
        try {
            redisTemplate.delete(redisKey);
        } catch (Exception e) {
            redisTemplateBak.delete(redisKey);
        }
    }
    @Override
    public void sortedSetAdd(String key, String value, double score) {
        try {
            redisTemplate.opsForZSet().add(key, value, score);
        } catch (Exception e) {
            redisTemplateBak.opsForZSet().add(key, value, score);
        }
    }

}
