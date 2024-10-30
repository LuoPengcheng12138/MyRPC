package com.luopc.myrpcversion5.client;

import com.luopc.myrpcversion5.common.Blog;
import com.luopc.myrpcversion5.common.User;
import com.luopc.myrpcversion5.service.BlogService;
import com.luopc.myrpcversion5.service.UserService;

public class TestClient {
    public static void main(String[] args) {
        RPCClient rpcClient = new SimpleRPCClient();

        RPCClientProxy clientProxy=new RPCClientProxy(rpcClient);

        UserService proxy = clientProxy.getProxy(UserService.class);

        User userByUserId = proxy.getUserByUserId(1);
        System.out.println("getUserByUserId:"+userByUserId);

        User user= User.builder().id(1).userName("lpc").sex(true).build();
        Integer integer=proxy.addUser(user);
        System.out.println("addUser:"+integer);

        // 客户中添加新的测试用例
        BlogService blogService = clientProxy.getProxy(BlogService.class);
        Blog blogById = blogService.getBlogById(10000);
        System.out.println("get blog from server：" + blogById);

    }
}
