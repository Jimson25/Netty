package com.bdht.netty.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len = msg.getLen();
        byte[] content = msg.getContent();

        System.out.println("客户端接收到消息如下");
        System.out.println("长度=" + len);
        System.out.println("内容=" + new String(content, StandardCharsets.UTF_8));

        System.out.println("客户端接收消息数量=" + (++this.count));
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String msg = "hello-world  ---fromClient";

        byte[] content = msg.getBytes(StandardCharsets.UTF_8);
        int length = content.length;
        MessageProtocol protocol = new MessageProtocol();
        protocol.setContent(content);
        protocol.setLen(length);
        for (int i = 0; i < 10; i++) {
            ctx.writeAndFlush(protocol);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}
