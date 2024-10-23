package com.luopc.myrpcversion5.server;

import com.luopc.myrpcversion5.service.BlogService;
import com.luopc.myrpcversion5.service.BlogServiceImpl;
import com.luopc.myrpcversion5.service.UserService;
import com.luopc.myrpcversion5.service.UserServiceImpl;

public class TestServer {
    public static void main(String[] args) {
        BlogService blogService = new BlogServiceImpl();
        UserService userService = new UserServiceImpl();
        ServiceProvider serviceProvider=new ServiceProvider();
        serviceProvider.provideServiceInterface(blogService);
        serviceProvider.provideServiceInterface(userService);
    ;

        RPCServer rpcServer=new NettyRPCServer(serviceProvider.getServiceProvide());
        rpcServer.start(8899);


    }
}
