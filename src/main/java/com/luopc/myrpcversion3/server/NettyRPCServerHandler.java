package com.luopc.myrpcversion3.server;

import com.luopc.myrpcversion3.common.RPCRequest;
import com.luopc.myrpcversion3.common.RPCResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

@AllArgsConstructor
public class NettyRPCServerHandler extends SimpleChannelInboundHandler<RPCRequest> {
    private HashMap<String,Object> serviceProvide;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RPCRequest msg) throws Exception {
        RPCResponse response=getResponse(msg);
        ctx.writeAndFlush(response);
        ctx.close();
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    RPCResponse getResponse(RPCRequest request) throws NoSuchMethodException {
        String interfaceName = request.getInterfaceName();
        Object service = serviceProvide.get(interfaceName);
        Method method=null;

        try {
            method=service.getClass().getMethod(request.getMethodName(),request.getParamTypes());
            Object object=method.invoke(service,request.getParams());
            return RPCResponse.success(object);

        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
            System.out.println("NettyRPCServerHandler method failed");
            return RPCResponse.fail();
        }

    }
}
