package com.bdht.netty.handlerdemo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //加入入站的handler进行解码 MyByteToLongDecoder
        pipeline.addLast("myServerDecoder", new MyByteToLongDecoder());
        pipeline.addLast("myServerEncoder", new MyLongToByteEncoder());

        pipeline.addLast("MyServerHandler", new MyServerHandler());

    }
}
