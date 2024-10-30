package com.luopc.myrpcversion5.client;

import com.luopc.myrpcversion5.common.RPCRequest;
import com.luopc.myrpcversion5.common.RPCResponse;
import com.luopc.myrpcversion5.register.ServiceRegister;
import com.luopc.myrpcversion5.register.ZkServiceRegister;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

@AllArgsConstructor
public class SimpleRPCClient implements RPCClient {
    private String host;
    private int port;
    private ServiceRegister serviceRegister;
    public SimpleRPCClient() {
        this.serviceRegister=new ZkServiceRegister();
    }
    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        //从注册中心获取host,post
        InetSocketAddress address=serviceRegister.serviceDiscovery(request.getInterfaceName());
        host=address.getHostName();
        port=address.getPort();
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
