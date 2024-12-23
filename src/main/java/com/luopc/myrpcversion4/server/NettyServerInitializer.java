package com.luopc.myrpcversion4.server;

import com.luopc.myrpcversion4.codec.JsonSerializer;
import com.luopc.myrpcversion4.codec.MyDecode;
import com.luopc.myrpcversion4.codec.MyEncode;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.AllArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
public class NettyServerInitializer extends ChannelInitializer<SocketChannel>{
    private HashMap<String,Object> serviceProvide;
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
//        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
//        pipeline.addLast(new LengthFieldPrepender(4));
//
//        pipeline.addLast(new ObjectEncoder());
//        pipeline.addLast(new ObjectDecoder(new ClassResolver() {
//            @Override
//            public Class<?> resolve(String className) throws ClassNotFoundException {
//                return Class.forName(className);
//            }
//        }));
        pipeline.addLast(new MyDecode());
        pipeline.addLast(new MyEncode(new JsonSerializer()));
        pipeline.addLast(new NettyRPCServerHandler(serviceProvide));
    }

}
