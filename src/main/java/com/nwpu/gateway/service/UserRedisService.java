package com.nwpu.gateway.service;


import com.nwpu.gateway.model.User;
import com.nwpu.gateway.model.UserRedisPO;

public interface UserRedisService {

    void saveUser(User user);

    UserRedisPO getUserById(String id);

    void updateUserNameAndAge(User user);
}
