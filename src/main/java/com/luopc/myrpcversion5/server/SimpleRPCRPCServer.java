package com.luopc.myrpcversion5.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class SimpleRPCRPCServer implements RPCServer{
    private HashMap<String, Object> serviceProvide;
    public SimpleRPCRPCServer(HashMap<String, Object> serviceProvide) {
        this.serviceProvide = serviceProvide;
    }
    @Override
    public void start(int port) {
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
            while(true){
                Socket socket = serverSocket.accept();
                new Thread(new WorkThread(socket,serviceProvide)).start();
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