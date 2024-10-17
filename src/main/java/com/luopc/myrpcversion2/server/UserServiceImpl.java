package com.luopc.myrpcversion2.server;

import com.luopc.myrpcversion2.common.User;
import com.luopc.myrpcversion2.service.UserService;
import java.util.Random;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Override
    public User getUserByUserId(Integer id) {
        System.out.println("客户端查询了"+id+"的用户");
        // 模拟从数据库中取用户的行为
        Random random = new Random();
        User user = User.builder().userName(UUID.randomUUID().toString())
                .id(id)
                .sex(random.nextBoolean()).build();
        return user;
    }
    @Override
    public Integer addUser(User user) {
        System.out.println("addUser"+user);
        return user.getId();
    }
}
