package com.luopc.myrpcversion5.server;

import com.luopc.myrpcversion5.register.ServiceRegister;
import com.luopc.myrpcversion5.register.ZkServiceRegister;
import lombok.AllArgsConstructor;

import java.net.InetSocketAddress;
import java.util.HashMap;

@AllArgsConstructor
public class ServiceProvider {
    private HashMap<String, Object> serviceProvide;
    private ServiceRegister serviceRegister;
    private String host;
    private int port;
    public ServiceProvider(String host, int port) {
        this.host = host;
        this.port = port;
        this.serviceRegister=new ZkServiceRegister();
        this.serviceProvide = new HashMap<>();
    }

    public void provideServiceInterface (Object service) {
        String key = service.getClass().getName();
        Class<?>[] clazz = service.getClass().getInterfaces();
        for(Class<?> interfaces : clazz) {
            serviceProvide.put(interfaces.getName(),service);
            serviceRegister.register(interfaces.getName(),new InetSocketAddress(host,port));
        }
    }
    public Object getService(String interfaceName){
        return serviceProvide.get(interfaceName);
    }
    public HashMap<String, Object> getServiceProvide(){
        return serviceProvide;
    }
}
