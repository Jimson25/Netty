package com.bdht.netty.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        //接收消息
        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("服务器接收到信息如下：");
        System.out.println("数据长度：" + len);
        System.out.println("数据内容：" + new String(content, StandardCharsets.UTF_8));
        System.out.println("服务器接收到消息次数：" + (++this.count));

        //回复消息
        byte[] messageBytes = UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8);
        int length = messageBytes.length;

        MessageProtocol protocol = new MessageProtocol();

        protocol.setLen(length);
        protocol.setContent(messageBytes);
        ctx.writeAndFlush(protocol);

    }
}
