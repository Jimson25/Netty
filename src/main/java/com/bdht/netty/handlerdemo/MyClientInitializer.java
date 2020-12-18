package com.bdht.netty.handlerdemo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //加入一个出站的handler对数据进行编码
        pipeline.addLast("MyClientEncoder", new MyLongToByteEncoder());

//        pipeline.addLast("MyClientDecoder", new MyByteToLongDecoder());
        pipeline.addLast("MyClientDecoder", new MyByteToLongDecoder2());
        //加入一个自定义的handler对数据进行处理
        pipeline.addLast("myClientHandler", new MyClientHandler());

    }
}
