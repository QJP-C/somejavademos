package com.qjp.redis;

import com.qjp.redis.service.CacheService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class HashTest {

    @Resource
    private CacheService cacheService;

    /**
     * 基本操作
     */
    @Test
    public void jb(){
        cacheService.hPut("key1", "hashKey1", "value1");
        System.out.println(cacheService.hPutIfAbsent("key1", "hashKey2", "value2"));
        System.out.println(cacheService.hGet("key1", "hashKey1"));
        System.out.println(cacheService.hExists("key1", "hashKey2"));//指定字段是否存在
        System.out.println(cacheService.hDelete("key1", "hashKey1", "hashKey2"));  //返回删除条数
        System.out.println(cacheService.hExists("key1", "hashKey1"));
    }

    /**
     * 批量操作
     */
    @Test
    public void testMulti(){
        Map<String, String> map = new HashMap<>();
        map.put("hashKey1", "value1");
        map.put("hashKey2", "value2");
        map.put("hashKey3", "value3");
        cacheService.hPutAll("key", map);                        //批量插入

        System.out.println(cacheService.hGetAll("key"));        //获取全部

        String[] s = {"hashKey1", "hashKey3"};
        List<Object> list = Arrays.asList(s);
        System.out.println(cacheService.hMultiGet("key", list));//批量获取 指定hashKey

        System.out.println(cacheService.hSize("key"));     //字段数量
        Set<Object> key = cacheService.hKeys("key");           //获取所有hashKey
        key.forEach(System.out::println);
        List<Object> hValues = cacheService.hValues("key");//获取所有值
        hValues.forEach(System.out::println);
    }

    /**
     * 自增
     */
    @Test
    public  void testIncr(){
        System.out.println(cacheService.hIncrBy("incr", "Integer", 3));     //不存在则创建  返回自增后的值
        System.out.println(cacheService.hIncrBy("incr", "Integer", -1));
        System.out.println(cacheService.hIncrByFloat("incr", "Float", 0.2));   //浮点型只能用浮点型自增
        System.out.println(cacheService.hIncrByFloat("incr", "Float", 1.0));
    }

}
