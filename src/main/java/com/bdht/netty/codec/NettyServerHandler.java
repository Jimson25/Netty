package com.bdht.netty.codec;

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
        //从客户端读取student数据
        StudentPOJO.Student student = (StudentPOJO.Student) msg;
        System.out.println("接收到客户端发送的数据 --- id=" + student.getId() + " --- name:" + student.getName());

    }

    /**
     * 读取事件结束后调用，向通道写入数据
     *
     * @param ctx 上下文对象，包含pipeline、channel、连接地址等
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello from server ", CharsetUtil.UTF_8));

    }

    /**
     * 发生异常时调用
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
