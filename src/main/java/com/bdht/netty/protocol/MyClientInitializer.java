package com.bdht.netty.protocol;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //对自定义数据协议进行编码
        pipeline.addLast(new MyMessageEncoder());
        pipeline.addLast(new MyClientHandler());
    }
}
