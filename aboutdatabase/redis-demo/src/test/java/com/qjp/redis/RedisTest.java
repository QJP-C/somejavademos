package com.qjp.redis;

import com.alibaba.fastjson.JSON;
import com.qjp.redis.dtos.Task;
import com.qjp.redis.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.lang.Nullable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RedisTest {
    @Resource
    private CacheService cacheService;


    /**
     * 对象的存取
     */
    @Test
    public void testObject() {
        Task task = new Task();
        task.setTaskType(1001);
        task.setPriority(1);
        task.setExecuteTime(new Date().getTime());
        cacheService.set("object", JSON.toJSONString(task));
        System.out.println(JSON.parseObject(cacheService.get("object"), Task.class));
    }



    /**
     * redis 获取key的两种方法
     */
    @Test
    public void testKeys() {
        Set<String> keys = cacheService.keys("topic:*");//获取所有不建议使用 易阻塞
        System.out.println(keys);
        Set<String> scan = cacheService.scan("topic:*");//迭代获取 建议使用
        System.out.println(scan);
    }

    /**
     * 普通方式去创建数据
     */
    @Test
    public void testPiple1() {//耗时5828
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Task task = new Task();
            task.setTaskType(1001);
            task.setPriority(1);
            task.setExecuteTime(new Date().getTime());
            cacheService.lLeftPush("1001_1", JSON.toJSONString(task));
        }
        System.out.println("耗时" + (System.currentTimeMillis() - start));
    }

    /**
     * redis管道技术批量创建
     */
    @Test
    public void testPiple2() {//耗时:1116毫秒
        long start = System.currentTimeMillis();
        //使用管道技术
        List<Object> objectList = cacheService.getstringRedisTemplate().executePipelined(new RedisCallback<Object>() {
            @Nullable
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                for (int i = 0; i < 10000; i++) {
                    Task task = new Task();
                    task.setTaskType(1001);
                    task.setPriority(1);
                    task.setExecuteTime(new Date().getTime());
                    redisConnection.lPush("1001_1".getBytes(), JSON.toJSONString(task).getBytes());
                }
                return null;
            }
        });
        System.out.println("使用管道技术执行10000次自增操作共耗时:" + (System.currentTimeMillis() - start) + "毫秒");
    }
}
