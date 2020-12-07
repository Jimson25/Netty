package com.bdht.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 自定义一个handler
 * 这里我们自定义的handler需要按照Netty规范继承一个handlerAdapter
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取数据的事件，这里可以读取客户端发送的消息
     *
     * @param ctx 上下文对象，包含pipeline、channel、连接地址等
     * @param msg 客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx = " + ctx);
        //将msg转换成byteBuf 这里的ByteBuf是NIO提供的
        ByteBuf buf = (ByteBuf) msg;

        System.out.println("接收到客户端消息：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端的地址是： " + ctx.channel().remoteAddress());

    }

    /**
     * 向通道写入数据
     * @param ctx 上下文对象，包含pipeline、channel、连接地址等
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello ", CharsetUtil.UTF_8));

    }

    /**
     * 发生异常时调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
