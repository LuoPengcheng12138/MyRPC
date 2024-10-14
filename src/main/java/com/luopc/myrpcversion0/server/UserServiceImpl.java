package com.luopc.myrpcversion0.server;

import com.luopc.myrpcversion0.common.User;
import com.luopc.myrpcversion0.service.UserService;

import java.util.Random;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Override
    public User getUserById(Integer id){
        System.out.println("客户端查询了"+id+"的用户");
        User user = new User();
        user.setId(id);
        user.setUsername(UUID.randomUUID().toString());
        user.setPassword("123456");
        return user;
    }
}