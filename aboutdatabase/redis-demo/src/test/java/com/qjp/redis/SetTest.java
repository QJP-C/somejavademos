package com.qjp.redis;

import com.qjp.redis.service.CacheService;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SetTest {

    @Resource
    private CacheService cacheService;
}
