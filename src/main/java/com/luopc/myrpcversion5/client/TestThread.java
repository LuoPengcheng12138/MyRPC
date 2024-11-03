package com.luopc.myrpcversion5.client;

import com.luopc.myrpcversion5.common.Blog;
import com.luopc.myrpcversion5.common.User;
import com.luopc.myrpcversion5.service.BlogService;
import com.luopc.myrpcversion5.service.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TestThread implements Runnable {
    private UserService userService;
    private BlogService blogService;
    @Override
    public void run() {
        User userByUserId = userService.getUserByUserId(1);
        //System.out.println("getUserByUserId:"+userByUserId);

//        User user= User.builder().id(1).userName("lpc").sex(true).build();
//        Integer integer=userService.addUser(user);
//        //System.out.println("addUser:"+integer);
//
//        Blog blogById = blogService.getBlogById(10000);
//        //System.out.println("get blog from server：" + blogById);
    }
}
