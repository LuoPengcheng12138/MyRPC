package com.luopc.myrpcversion4.client;

import com.luopc.myrpcversion4.common.RPCRequest;
import com.luopc.myrpcversion4.common.RPCResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

public class NettyRPCClient implements RPCClient {
    private static final Bootstrap bootstrap ;
    private static final EventLoopGroup eventLoopGroup ;
    private String host ;
    private int port ;
    public NettyRPCClient(String host, int port) {
        this.host = host ;
        this.port = port ;
    }

    static {
        bootstrap = new Bootstrap();
        eventLoopGroup = new NioEventLoopGroup() ;
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer());
    }

    @Override
    public RPCResponse sendRequest(RPCRequest request) throws InterruptedException {
        try{
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            Channel channel = channelFuture.channel();

            channel.writeAndFlush(request);
            channel.closeFuture().sync();

            AttributeKey<RPCResponse> key = AttributeKey.valueOf("RPCResponse");
            RPCResponse response = channel.attr(key).get();
            System.out.println(response);
            return response;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
