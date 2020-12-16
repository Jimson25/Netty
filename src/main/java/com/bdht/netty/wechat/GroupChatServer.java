package com.bdht.netty.wechat;

import com.bdht.netty.wechat.handler.WeChatServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class GroupChatServer {
    private final int PORT;

    public GroupChatServer(int port) {
        PORT = port;
    }

    private void run() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //从上下文获取到pipeline
                            ChannelPipeline pipeline = ch.pipeline();

                            //加入解码器
                            pipeline.addLast("decoder", new StringDecoder());
                            //加入编码器
                            pipeline.addLast("encoder", new StringEncoder());
                            //加入自定义的业务处理handler
                            pipeline.addLast(new WeChatServerHandler());
                        }
                    });


            System.out.println("服务器在 " + PORT + "端口上启动");
            ChannelFuture channelFuture = bootstrap.bind(PORT).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
           e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }


    public static void main(String[] args) {
        new GroupChatServer(8888).run();
    }


}
