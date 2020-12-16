package com.bdht.netty.wechat;

import com.bdht.netty.wechat.handler.WeChatClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class GroupChatClient {
    private final String HOST;
    private final int PORT;

    public GroupChatClient(String host, int port) {
        HOST = host;
        PORT = port;
    }

    public void run() {
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(nioEventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //得到pipeline
                        ChannelPipeline pipeline = ch.pipeline();
                        //加入解码器
                        pipeline.addLast("decoder", new StringDecoder());
                        //加入编码器
                        pipeline.addLast("encoder", new StringEncoder());
                        //加入自定义handler
                        pipeline.addLast(new WeChatClientHandler());
                    }
                });

        try {
            ChannelFuture channelFuture = bootstrap.connect(HOST, PORT).sync();
            System.out.println("------ CLIENT IS READY ------");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                channelFuture.channel().writeAndFlush(msg);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new GroupChatClient("127.0.0.1", 8888).run();
    }


}
