package com.luopc.myrpcversion5.client;

import com.luopc.myrpcversion5.common.RPCRequest;
import com.luopc.myrpcversion5.common.RPCResponse;
import com.luopc.myrpcversion5.register.ServiceRegister;
import com.luopc.myrpcversion5.register.ZkServiceRegister;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

public class NettyRPCClient implements RPCClient {
    private static final Bootstrap bootstrap ;
    private static final EventLoopGroup eventLoopGroup ;
    private String host ;
    private int port ;
    private ServiceRegister serviceRegister;

    public NettyRPCClient() {
        this.serviceRegister=new ZkServiceRegister();
    }

    static {
        bootstrap = new Bootstrap();
        eventLoopGroup = new NioEventLoopGroup() ;
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer());
    }

    @Override
    public RPCResponse sendRequest(RPCRequest request) throws InterruptedException {
        //从注册中心获取host,post
        InetSocketAddress address=serviceRegister.serviceDiscovery(request.getInterfaceName());
        host=address.getHostName();
        port=address.getPort();
        try{
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            Channel channel = channelFuture.channel();

            channel.writeAndFlush(request);
            channel.closeFuture().sync();

            AttributeKey<RPCResponse> key = AttributeKey.valueOf("RPCResponse");
            RPCResponse response = channel.attr(key).get();

            //System.out.println(response);
            return response;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
