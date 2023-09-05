package com.qjp.redis;

import com.qjp.redis.service.CacheService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ListTest {

    @Resource
    private CacheService cacheService;
    @Test
    public void addList() {
        for (int i = 20; i >= 1; i--) cacheService.lLeftPush("list", "value" + i);
    }
    @Test
    public void addList1() {for (int i = 6; i >= 1; i--) cacheService.lLeftPush("list1", "value" + i);}

    /**
     * 基本操作
     */
    @Test
    public void testJb(){
        //在list左边添加元素
        System.out.println(cacheService.lLeftPush("leftAndRight", "left1"));
        System.out.println(cacheService.lLeftPush("leftAndRight", "left1", "插队"));//如果pivot存在,再pivot前面添加
        System.out.println(cacheService.lLeftPushIfPresent("leftAndRight", "left2"));
        //右边添加
        System.out.println(cacheService.lRightPush("leftAndRight", "right1"));
        System.out.println(cacheService.lRightPush("leftAndRight", "right1", "insert"));//如果pivot存在,在pivot元素的右边添加值
        System.out.println(cacheService.lRightPushIfPresent("leftAndRight", "right2"));
        //在list左边获取元素并删除
        System.out.println(cacheService.lRightPop("list1"));
        //在list右边获取元素并删除
        System.out.println(cacheService.lLeftPop("list1"));

        System.out.println(cacheService.lIndex("list", 3)); //获取指定索引的值

        //获取长度
        System.out.println(cacheService.lLen("list"));
    }

    /**
     * 批量操作
     */
    @Test
    public void testMulti(){
        List<String> values = cacheService.lRange("list", 3, 8); //获取列表指定范围内的元素 索引
        values.forEach(System.out::println);

        String[] ss = {"all1", "all2", "all3"};
        List<String> list = Arrays.asList(ss);
        System.out.println(cacheService.lLeftPushAll("listAll1", list));                    //批量头插1
        System.out.println(cacheService.lLeftPushAll("listAll2", "all1", "all2", "all3"));  //批量头插2
        //lRightPushAll同lLeftPushAll

    }

    /**
     * 阻塞获取
     */
    @Test
    public void testClog(){
        //移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
        System.out.println(cacheService.lBLeftPop("list1", 5, TimeUnit.MINUTES));
        //移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
        System.out.println(cacheService.lBRightPop("list1", 5, TimeUnit.MINUTES));
    }

    /**
     * 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
     */
    @Test
    public void testTransfer(){
        System.out.println(cacheService.lRightPopAndLeftPush("list1", "list"));
        //(阻塞版) 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
        System.out.println(cacheService.lBRightPopAndLeftPush("list1", "list",5,TimeUnit.MINUTES));
    }

    /**
     * 删除集合中值等于value得元素
     * index=0, 删除所有值等于value的元素; index>0, 从头部开始删除第一个值等于value的元素;
     * index<0, 从尾部开始删除第一个值等于value的元素;
     */
    @Test
    public void testLRemove(){
        cacheService.lRemove("list1",1,"value3");
    }

    /**
     * 裁剪list
     */
    @Test
    public void testLTrim(){
        cacheService.lTrim("list1",1,3);
    }

}
