package com.luopc.myrpcversion3.server;

import com.luopc.myrpcversion2.service.BlogService;
import com.luopc.myrpcversion2.service.UserService;

import java.util.HashMap;

public class TestServer {
    public static void main(String[] args) {
        BlogService blogService = new BlogServiceImpl();
        UserService userService = new UserServiceImpl();
        ServiceProvider serviceProvider=new ServiceProvider();
        serviceProvider.provideServiceInterface(blogService);
        serviceProvider.provideServiceInterface(userService);
        //SimpleRPCRPCServer simpleRPCRPCServer=new SimpleRPCRPCServer(serviceProvider.getServiceProvide());
        //simpleRPCRPCServer.start(8899);
        ThreadPoolRPCRPCServer threadPoolRPCRPCServer=new ThreadPoolRPCRPCServer(serviceProvider.getServiceProvide());
        threadPoolRPCRPCServer.start(8899);


    }
}
