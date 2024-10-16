package com.luopc.myrpcversion1.server;

import com.luopc.myrpcversion1.common.RPCRequest;
import com.luopc.myrpcversion1.common.RPCResponse;
import com.luopc.myrpcversion1.common.User;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class RPCServer {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        try {
            ServerSocket serverSocket = new ServerSocket(8899);
            System.out.println("服务端启动了");
            // BIO的方式监听Socket
            while (true){
                Socket socket = serverSocket.accept();
                // 开启一个线程去处理
                new Thread(()->{
                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        RPCRequest request = (RPCRequest) ois.readObject();
                        //method 是 userService 里的方法
                        Method method= userService.getClass().getMethod(request.getMethodName(),request.getParamTypes());
                        //invoke 方法并没有调用 ClientProxy 类中重写的方法，它调用的是 Method 类的 invoke 方法
                        Object object=method.invoke(userService,request.getParams());
                        oos.writeObject(RPCResponse.success(object));
                        oos.flush();

                    } catch (IOException |NoSuchMethodException|ClassNotFoundException e){
                        e.printStackTrace();
                        System.out.println("从IO中读取数据错误");
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器启动失败");
        }
    }
}
