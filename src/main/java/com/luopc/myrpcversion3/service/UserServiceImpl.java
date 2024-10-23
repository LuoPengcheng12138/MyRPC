package com.luopc.myrpcversion3.service;

import com.luopc.myrpcversion3.common.User;

import java.util.Random;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Override
    public User getUserByUserId(Integer id) {
        System.out.println("client query "+id+" 's User");
        // 模拟从数据库中取用户的行为
        Random random = new Random();
        User user = User.builder().userName(UUID.randomUUID().toString())
                .id(id)
                .sex(random.nextBoolean()).build();
        return user;
    }
    @Override
    public Integer addUser(User user) {
        System.out.println("addUser "+user);
        return user.getId();
    }
}
