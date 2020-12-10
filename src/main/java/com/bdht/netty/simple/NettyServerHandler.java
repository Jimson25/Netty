package com.bdht.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

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
        //用户程序自定义普通任务，这里添加了两个线程去执行任务，这两个线程会被添加到上下文的taskQueue中
        ctx.channel().eventLoop().execute(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer("hello from server -- 1 ", CharsetUtil.UTF_8));
        });

        ctx.channel().eventLoop().execute(() -> {
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer("hello from server -- 2 ", CharsetUtil.UTF_8));
        }); //end

        //用户自定义的定时任务
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello from server -- 3 ", CharsetUtil.UTF_8));
            }
        },5, TimeUnit.SECONDS);

        System.out.println("server ctx = " + ctx);
        //将msg转换成byteBuf 这里的ByteBuf是NIO提供的
        ByteBuf buf = (ByteBuf) msg;

        System.out.println("接收到客户端消息：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端的地址是： " + ctx.channel().remoteAddress());

    }

    /**
     * 读取事件结束后调用，向通道写入数据
     * @param ctx 上下文对象，包含pipeline、channel、连接地址等
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello from server ", CharsetUtil.UTF_8));

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
