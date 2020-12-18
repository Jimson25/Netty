package com.bdht.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MyMessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        System.out.println("\n MyMessageDecoder.decode 被调用");
        int i = in.readInt();
        byte[] bytes = new byte[i];
        in.readBytes(bytes);

        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(i);
        messageProtocol.setContent(bytes);

        out.add(messageProtocol);


    }
}
