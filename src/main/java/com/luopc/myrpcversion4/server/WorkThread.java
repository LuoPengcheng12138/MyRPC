package com.luopc.myrpcversion4.server;

import com.luopc.myrpcversion4.common.RPCRequest;
import com.luopc.myrpcversion4.common.RPCResponse;
import lombok.AllArgsConstructor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
@AllArgsConstructor
public class WorkThread implements Runnable {
    private Socket socket;
    private HashMap<String,Object> serviceProvide;
    @Override
    public void run(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            RPCRequest request = (RPCRequest) ois.readObject();

            Method method=serviceProvide.get(request.getInterfaceName()).getClass().getMethod(request.getMethodName(), request.getParamTypes());
            Object object= method.invoke(serviceProvide.get(request.getInterfaceName()), request.getParams());

            oos.writeObject(RPCResponse.success(object));
            oos.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Server Error");
        }
    }
}
