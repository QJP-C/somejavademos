package com.qjp.mp.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qjp.mp.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 自定义sql
     * 注解
     * @return
     */
    @Select("select * from user")
    List<User> selectRaw();

    /**
     * xml
     * @return
     */
    List<User> selectRaw1();
}
