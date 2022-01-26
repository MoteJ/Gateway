package com.nwpu.gateway.controller;

import com.nwpu.gateway.model.User;
import com.nwpu.gateway.model.UserRedisPO;
import com.nwpu.gateway.service.UserRedisService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mote
 * @date 2020/9/21 19:04
 */
@RestController("/redis")
@AllArgsConstructor
public class RedisController {

    UserRedisService userRedisService;

    @RequestMapping("/insert")
    @ResponseBody
    public User insertUser() {
        User userRedis = User.builder()
                .age(23)
                .id("123456")
                .name("nima")
                .build();
        userRedisService.saveUser(userRedis);
        return userRedis;
    }

    @RequestMapping("/getUser")
    @ResponseBody
    public UserRedisPO getUser() {
        return userRedisService.getUserById("123456");
    }

}
