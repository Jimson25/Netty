package com.bdht.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) {
        /**
         * 创建BossGroup
         *  BossGroup主要用于接受服务端发送的请求
         */
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();

        /**
         * 创建workerGroup
         *  WorkerGroup主要用于处理业务
         */
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        //创建服务器启动对象，配置参数
        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(bossGroup, workerGroup)      //设置两个线程组
                .channel(NioServerSocketChannel.class)      //使用NIOSocketChannel作为服务器通道
                .option(ChannelOption.SO_BACKLOG, 128)   //设置线程队列得到的连接个数
                .childOption(ChannelOption.SO_KEEPALIVE, true)   //设置保持活动连接状态
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    //给pipeline设置处理器
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new NettyServerHandler());
                    }
                });


        try {
            //绑定一个端口并同步处理   启动服务器并绑定端口
            ChannelFuture channelFuture = bootstrap.bind(8888).sync();

            //监听通道关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
