package com.example.usercenter;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class RedisTest {

    @Resource
    private RedisTemplate  redisTemplate;

    @Test
    public void test(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("name","zhangsan");
        System.out.println(valueOperations.get("name"));
        valueOperations.set("yupiInteger",1);
        System.out.println(valueOperations.get("yupiInteger"));
        valueOperations.set("yupiFloat",1.1);
        System.out.println(valueOperations.get("yupiFloat"));
        valueOperations.set("yupiBoolean",true);
        System.out.println(valueOperations.get("yupiBoolean"));
    }
}
