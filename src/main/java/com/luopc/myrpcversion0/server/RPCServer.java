package com.luopc.myrpcversion0.server;

import com.luopc.myrpcversion0.common.User;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RPCServer {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        try (ServerSocket serverSocket = new ServerSocket(8879)) {
            System.out.println("server start...");
            Socket socket = serverSocket.accept();

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            System.out.println("server new Thread...");

            new Thread(() -> {
                System.out.println("server new Thread...");
                while (true) {
                    System.out.println("waiting for connection...");
                    try{
                        int id= objectInputStream.readInt();
                        System.out.println("接收到的信息: " + id);

                        User usr=userService.getUserById(id);
                        objectOutputStream.writeObject(usr);
                        objectOutputStream.flush();

                    } catch (EOFException e) {
                        System.out.println("EOFException");
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        System.out.println("IOException");
                        throw new RuntimeException(e);
                    }

                }
            }).start();
//            System.out.println("服务端启动了");
//            // BIO的方式监听Socket
//            while (true){
//                Socket socket = serverSocket.accept();
//                // 开启一个线程去处理
//                new Thread(()->{
//                    try {
//                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//                        // 读取客户端传过来的id
//                        Integer id = ois.readInt();
//                        User userByUserId = userService.getUserById(id);
//                        // 写入User对象给客户端
//                        oos.writeObject(userByUserId);
//                        oos.flush();
//                    } catch (IOException e){
//                        e.printStackTrace();
//                        System.out.println("从IO中读取数据错误");
//                    }
//                }).start();
//           }

        } catch (Exception e) {
            System.out.println("服务端读取失败");
            throw new RuntimeException(e);

        }

    }
}