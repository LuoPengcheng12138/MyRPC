package com.luopc.myrpcversion4.client;

import com.luopc.myrpcversion4.common.RPCRequest;
import com.luopc.myrpcversion4.common.RPCResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@AllArgsConstructor
public class SimpleRPCClient implements RPCClient {
    private String host;
    private int port;
    @Override
    public RPCResponse sendRequest(RPCRequest request) {
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
