package com.luopc.myrpcversion5.client;


import com.luopc.myrpcversion5.service.BlogService;
import com.luopc.myrpcversion5.service.UserService;
import lombok.extern.slf4j.Slf4j;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class TestClient {
    public static void main(String[] args) {
        RPCClient rpcClient = new NettyRPCClient();

        RPCClientProxy clientProxy=new RPCClientProxy(rpcClient);

        UserService userService = clientProxy.getProxy(UserService.class);
        BlogService blogService = clientProxy.getProxy(BlogService.class);

        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                100,60, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());
        int i=10000;  //10000用时2.6s qps：3800

        long start = System.currentTimeMillis();
        while(i-- !=0){
            threadPoolExecutor.execute(new TestThread(userService,blogService));

        }
        threadPoolExecutor.shutdown();

        while(true) {
            if (threadPoolExecutor.isTerminated()) {
                //System.out.println("Finally do something ");
                long end = System.currentTimeMillis();
                System.out.println("用时: " + (end - start) + "ms");
                break;
            }

        }

    }
}
