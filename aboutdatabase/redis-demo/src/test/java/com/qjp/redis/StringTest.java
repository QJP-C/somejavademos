package com.qjp.redis;

import com.qjp.redis.service.CacheService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StringTest {

    @Resource
    private CacheService cacheService;
    @Test
    public void testSet(){

        cacheService.set("key1","kkkkkk",5L,TimeUnit.MINUTES);
        cacheService.set("key2","kkkkkk",5L,TimeUnit.MINUTES);
    }

    @Test
    public void test1(){
        System.out.println(cacheService.getExpire("key2"));//获取剩余时间  秒
        System.out.println(cacheService.getExpire("key2", TimeUnit.MINUTES));//获取指定单位的剩余时间
        System.out.println(cacheService.persist("key2"));//移除key的过期时间 使其持久化
    }

    @Test
    public void test2(){
        System.out.println(cacheService.randomKey());//随机获取一个key
        System.out.println(cacheService.renameIfAbsent("key1", "kiss"));//修改key名称(如果新key不存在)
        System.out.println(cacheService.getAndSet("kiss", "kiss"));//获取并重写值
        System.out.println(cacheService.get("kiss"));
        cacheService.set("append", "append");
        System.out.println(cacheService.append("append", "s")); //追加字符 返回追加起始索引
        Set<String> scan = cacheService.scan("*k*"); //获取指定条件的key   包含k的key
        scan.forEach(System.out::println);
    }

    /**
     * 部分覆写
     */
    @Test
    public void test3(){
        cacheService.set("kk", "vvv");
        System.out.println(cacheService.get("kk"));
        cacheService.setRange("kk", "ll", 1);  //部分替换值（覆写） 从1索引位置开始
        System.out.println(cacheService.get("kk"));
    }

    /**
     * 批量操作
     */
    @Test
    public void test4(){
        Map<String, String> map = new HashMap<>();
        map.put("multi1", "multi1");
        map.put("multi2", "multi2");
        map.put("multi3", "multi3");
        cacheService.multiSet(map); //批量设置值
//        cacheService.multiSetIfAbsent(map);
        ArrayList<String> list = new ArrayList<>();
        list.add("multi1");
        list.add("multi2");
        list.add("multi3");
        List<String> strings = cacheService.multiGet(list); //批量获取值
        strings.forEach(System.out::println);
    }

}
