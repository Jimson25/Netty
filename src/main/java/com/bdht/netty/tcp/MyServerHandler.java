package com.bdht.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);

        String message = new String(bytes, StandardCharsets.UTF_8);
        System.out.println("服务器接收到数据：" + message);

        System.out.println("服务器接收到数据次数" + (++count));

        ByteBuf byteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString(), StandardCharsets.UTF_8);
        ctx.writeAndFlush(byteBuf);

    }
}
