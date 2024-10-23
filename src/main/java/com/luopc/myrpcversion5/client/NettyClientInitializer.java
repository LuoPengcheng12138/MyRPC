package com.luopc.myrpcversion5.client;

import com.luopc.myrpcversion5.codec.JsonSerializer;
import com.luopc.myrpcversion5.codec.MyDecode;
import com.luopc.myrpcversion5.codec.MyEncode;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new MyDecode());
        pipeline.addLast(new MyEncode(new JsonSerializer()));
        pipeline.addLast(new NettyClientHandler());
    }
}
