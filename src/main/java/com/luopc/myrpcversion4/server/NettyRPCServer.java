package com.luopc.myrpcversion4.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.AllArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
public class NettyRPCServer implements RPCServer {
    private HashMap<String, Object> serviceProvide;
    @Override
    public void start(int port){
        // netty 服务线程组boss负责建立连接， work负责具体的请求
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        System.out.printf("Netty Server started on port %d\n...",port);
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new NettyServerInitializer(serviceProvide));
            ChannelFuture channelFuture = serverBootstrap.bind(port).syncUninterruptibly();
            channelFuture.channel().closeFuture().syncUninterruptibly();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    @Override
    public void stop(){

    }

}
