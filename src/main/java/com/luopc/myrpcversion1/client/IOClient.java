package com.luopc.myrpcversion1.client;

import com.luopc.myrpcversion1.common.RPCRequest;
import com.luopc.myrpcversion1.common.RPCResponse;
import com.luopc.myrpcversion1.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class IOClient {
    //声明为静态函数
    public static RPCResponse send(RPCRequest request,String host,int port) {
        try {
            Socket socket = new Socket(host, port);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            // 返回 RPCResponse 结构
            RPCResponse response  = (RPCResponse) objectInputStream.readObject();

            return response;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("send failure");
            return null;
        }
    }
}
