package com.luopc.myrpcversion2.client;

import com.luopc.myrpcversion2.common.User;
import com.luopc.myrpcversion2.service.UserService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Proxy;
import java.net.Socket;
import java.util.Random;

public class RPCClient {
    public static void main(String[] args) {
        ClientProxy clientProxy=new ClientProxy("127.0.0.1",8899);
        UserService proxy = clientProxy.getProxy(UserService.class);

        User userByUserId = proxy.getUserByUserId(1);
        System.out.println("getUserByUserId:"+userByUserId);

        User user= User.builder().id(1).userName("lpc").sex(true).build();
        Integer integer=proxy.addUser(user);
        System.out.println("addUser:"+integer);

    }
}
