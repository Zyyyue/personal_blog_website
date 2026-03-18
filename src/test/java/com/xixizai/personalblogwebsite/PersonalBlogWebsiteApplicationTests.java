package com.xixizai.personalblogwebsite;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class PersonalBlogWebsiteApplicationTests {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void testForRedis(){
        String key="name";
        String value="陈浩然";
        stringRedisTemplate.opsForValue().set(key,value);
        System.out.println(stringRedisTemplate.opsForValue().get(key));
    }

}
