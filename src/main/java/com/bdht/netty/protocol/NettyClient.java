package com.bdht.netty.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    public static void main(String[] args) {
        //启动一个事件循环组
        NioEventLoopGroup executors = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(executors)  //加入一个线程组
                    .channel(NioSocketChannel.class)    //设置客户端通讯的实现类
                    .handler(new MyClientInitializer());

            System.out.println("客户端启动~~");


            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8888).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executors.shutdownGracefully();
        }


    }
}
