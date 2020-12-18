package com.bdht.netty.handlerdemo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyServerHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("从客户端: " + ctx.channel().remoteAddress() + " 读取到: " + msg);
        System.out.println("向客户端发送数据：" + 9876541L);
        ctx.writeAndFlush(9876541L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
        cause.printStackTrace();
    }
}
