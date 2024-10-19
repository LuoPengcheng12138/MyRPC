package com.luopc.myrpcversion3.service;

import com.luopc.myrpcversion3.common.User;

public interface UserService {
    // 客户端通过这个接口调用服务端的实现类
    User getUserByUserId(Integer id);
    Integer addUser(User user);
}
