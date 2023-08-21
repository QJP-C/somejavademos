package com.qjp.mp.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qjp.mp.po.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> { }
