package com.luopc.myrpcversion3.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.luopc.myrpcversion3.common.RPCRequest;
import com.luopc.myrpcversion3.common.RPCResponse;

public class ThreadPoolRPCRPCServer implements RPCServer {
    private ThreadPoolExecutor threadPoolExecutor;
    private HashMap<String, Object> serviceProvide;
    public ThreadPoolRPCRPCServer(HashMap<String, Object> serviceProvide) {
        threadPoolExecutor=new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),100,60, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());
        this.serviceProvide=serviceProvide;
    }
    public ThreadPoolRPCRPCServer(HashMap<String, Object> serviceProvide,
                                  int corePoolSize,
                                  int maximumPoolSize,
                                  long keepAliveTime,
                                  TimeUnit unit,
                                  BlockingQueue<Runnable> workQueue) {
        threadPoolExecutor=new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue);
        this.serviceProvide=serviceProvide;

    }
    @Override
    public void start(int port) {
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
            while(true){
                Socket socket = serverSocket.accept();
                threadPoolExecutor.execute(new WorkThread(socket,serviceProvide));

            }

        }
        catch (IOException e) {
            System.out.println("Server Error");
            e.printStackTrace();
        }
    }
    @Override
    public void stop() {

    }
}
