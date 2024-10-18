package com.luopc.myrpcversion2.server;

import lombok.AllArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
public class ServiceProvider {
    private HashMap<String, Object> serviceProvide;
    public ServiceProvider(){
        serviceProvide = new HashMap<>();
    }

    public void provideServiceInterface (Object service) {
        String key = service.getClass().getName();
        Class<?>[] clazz = service.getClass().getInterfaces();
        for(Class<?> interfaces : clazz) {
            serviceProvide.put(interfaces.getName(),service);
        }
    }
    public Object getService(String interfaceName){
        return serviceProvide.get(interfaceName);
    }
    public HashMap<String, Object> getServiceProvide(){
        return serviceProvide;
    }
}
