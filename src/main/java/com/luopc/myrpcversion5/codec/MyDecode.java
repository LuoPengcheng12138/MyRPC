package com.luopc.myrpcversion5.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MyDecode extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 1. 读取消息类型
        short messageType = in.readShort();
        //System.out.println("messageType="+messageType);

        if(messageType != MessageType.REQUEST.getCode() &&
                messageType != MessageType.RESPONSE.getCode()){
            System.out.println("MyDecode：Not Support This Type Message");
            return;
        }
        // 2. 读取序列化的类型
        short serializerType = in.readShort();
        //System.out.println("serializerType="+serializerType);
        // 根据类型得到相应的序列化器
        Serializer serializer=Serializer.getSerializerByCode(serializerType);
        if(serializer == null)throw new RuntimeException("No Match Serializer");
        // 3. 读取数据序列化后的字节长度
        int length = in.readInt();
        //System.out.println("length="+length);

        // 4. 读取序列化数组
        byte[] data = new byte[length];
        in.readBytes(data);
        Object deserialize = serializer.deserialize(data, messageType);
        //System.out.println("deserialize="+deserialize);
        out.add(deserialize);
    }
}
