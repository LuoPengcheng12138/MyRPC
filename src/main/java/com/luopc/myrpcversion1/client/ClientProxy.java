package com.luopc.myrpcversion1.client;

import com.luopc.myrpcversion1.common.RPCRequest;
import com.luopc.myrpcversion1.common.RPCResponse;
import lombok.Builder;
import org.apache.zookeeper.server.Request;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


@Builder
public class ClientProxy implements InvocationHandler {
    private String host;
    private int port;
    //jdk 动态代理， 每一次代理对象调用方法，会经过此方法增强（反射获取request对象，socket发送至客户端）
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RPCRequest request= RPCRequest.builder().interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName()).params(args).paramTypes(method.getParameterTypes()).build();
        RPCResponse response=IOClient.send(request,host,port);
        return response.getData();
    }

    <T>T getProxy(Class<T> clazz){
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T)o;
    }

}
