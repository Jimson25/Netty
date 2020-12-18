package com.bdht.netty.handlerdemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyByteToLongDecoder extends ByteToMessageDecoder {

    /**
     * @param ctx 上下文对象
     * @param in  入站的bytebuf
     * @param out list集合，将解码后的数据传给下一个handler
     * @throws Exception 异常信息
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }

    }
}
