package com.nwpu.gateway.service.impl;


import com.nwpu.gateway.helper.RedisKeyHelper;
import com.nwpu.gateway.infrastructure.redis.RedisRepository;
import com.nwpu.gateway.model.User;
import com.nwpu.gateway.model.UserRedisPO;
import com.nwpu.gateway.service.UserRedisService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.hash.BeanUtilsHashMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserRedisServiceImpl implements UserRedisService {

    private final RedisRepository redisRepository;

    private static BeanUtilsHashMapper<UserRedisPO> userRedisHashMapper =
            new BeanUtilsHashMapper<>(UserRedisPO.class);

    @Override
    public void saveUser(User user) {
        Map<String, String> redisMap = toRedisMap(user);
        Map<String, String> filterMap = new HashMap<String, String>(3);
        String redisKey = RedisKeyHelper.getUserRedisKey(user.getId());
        for (String key : redisMap.keySet()) {
            filterMap.put(key, filterNullValue(redisMap.get(key)));
        }
        redisRepository.hashPutAll(redisKey, filterMap);
    }

    @Override
    public UserRedisPO getUserById(String id) {
        String redisKey = RedisKeyHelper.getUserRedisKey(id);
        if (!redisRepository.hasKey(redisKey)) {
            return null;
        }
        Map<String, String> redisMap = redisRepository.hashGetAll(redisKey);
        if (StringUtils.isBlank(redisMap.get("id"))) {
            return null;
        }
        return userRedisHashMapper.fromHash(redisMap);
    }

    @Override
    public void updateUserNameAndAge(User user) {

    }

    private Map<String, String> toRedisMap(User user) {
        Map<String, String> map = new HashMap<String, String>(3);
        map.put("name", user.getName());
        map.put("id", user.getId());
        map.put("age", String.valueOf(user.getAge()));
        return map;
    }
    private String filterNullValue(String value) {
        return value == null ? "" :value;
    }
}
