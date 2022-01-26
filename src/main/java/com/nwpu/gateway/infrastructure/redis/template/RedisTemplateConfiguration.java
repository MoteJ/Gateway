package com.nwpu.gateway.infrastructure.redis.template;


import com.nwpu.gateway.infrastructure.redis.config.RedisPropertiesBak;
import com.nwpu.gateway.infrastructure.redis.config.RedisPropertiesBase;
import com.nwpu.gateway.infrastructure.redis.config.RedisPropertiesMaster;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class RedisTemplateConfiguration {

    @Resource
    private RedisPropertiesMaster redisPropertiesMaster;

    @Resource
    private RedisPropertiesBak redisPropertiesBak;

    private JedisPoolConfig getJedisPoolConfig(Integer maxActive, Integer maxIdle, Integer minIdle,
                                               Integer maxWait, Boolean isBlockWhenExhausted,
                                               Boolean isTestOnBorrow, Boolean isTestOnReturn) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        jedisPoolConfig.setBlockWhenExhausted(isBlockWhenExhausted);
        jedisPoolConfig.setTestOnBorrow(isTestOnBorrow);
        jedisPoolConfig.setTestOnReturn(isTestOnReturn);
        return jedisPoolConfig;
    }

    private RedisClusterConfiguration getJedisCluster(int maxRedirects, String nodes, String password) {
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        redisClusterConfiguration.setMaxRedirects(maxRedirects);
        redisClusterConfiguration.setPassword(password);

        List<RedisNode> redisNodes = new ArrayList<>();
        String[] cNodes = nodes.split(",");
        for (String node : cNodes) {
            String[] hp = node.split(":");
            redisNodes.add(new RedisNode(hp[0], Integer.parseInt(hp[1])));
        }
        redisClusterConfiguration.setClusterNodes(redisNodes);
        return redisClusterConfiguration;
    }

    private JedisConnectionFactory getJedisConnectionFactory(RedisClusterConfiguration redisClusterConfiguration,
                                                             JedisPoolConfig jedisPoolConfig) {
        JedisConnectionFactory jedisConnectionFactory =
                new JedisConnectionFactory(redisClusterConfiguration, jedisPoolConfig);
        jedisConnectionFactory.afterPropertiesSet();
        return jedisConnectionFactory;
    }

    private StringRedisTemplate createRedisTemplate(RedisPropertiesBase config) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(this.getJedisConnectionFactory(
                this.getJedisCluster(config.getMaxRedirects(), config.getNodes(), config.getPassword()),
                this.getJedisPoolConfig(config.getMaxActive(), config.getMaxIdle(), config.getMinIdle(),
                        config.getMaxWait(), config.getBlockWhenExhausted(), config.getTestOnBorrow(), config.getTestOnReturn()))
        );
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean("clusters-master")
    public StringRedisTemplate redisTemplateMaster() {
        return this.createRedisTemplate(this.redisPropertiesMaster);
    }

    @Bean("clusters-bak")
    public StringRedisTemplate redisTemplateBak() {
        return this.createRedisTemplate(this.redisPropertiesBak);
    }
}
