package com.qjp.mp.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qjp.mp.mappers.UserMapper;
import com.qjp.mp.po.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{
}
