package com.luopc.myrpcversion3.server;

import com.luopc.myrpcversion3.service.BlogService;
import com.luopc.myrpcversion3.service.BlogServiceImpl;
import com.luopc.myrpcversion3.service.UserService;
import com.luopc.myrpcversion3.service.UserServiceImpl;

public class TestServer {
    public static void main(String[] args) {
        BlogService blogService = new BlogServiceImpl();
        UserService userService = new UserServiceImpl();
        ServiceProvider serviceProvider=new ServiceProvider();
        serviceProvider.provideServiceInterface(blogService);
        serviceProvider.provideServiceInterface(userService);
        //SimpleRPCRPCServer simpleRPCRPCServer=new SimpleRPCRPCServer(serviceProvider.getServiceProvide());
        //simpleRPCRPCServer.start(8899);
        //ThreadPoolRPCRPCServer threadPoolRPCRPCServer=new ThreadPoolRPCRPCServer(serviceProvider.getServiceProvide());
        //threadPoolRPCRPCServer.start(8899);

        RPCServer rpcServer=new NettyRPCServer(serviceProvider.getServiceProvide());
        rpcServer.start(8899);


    }
}
