package com.bdht.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //向管道加入处理器

        //1.获取管道
        ChannelPipeline pipeline = ch.pipeline();
        //2.加入一个netty提供的httpServerCodec
        pipeline.addLast("myHttpServerCodec", new HttpServerCodec());
        //3. 加入自定义的HttpHandler
        pipeline.addLast("myTestHttpServerHandler",new TestHttpServerHandler());

    }
}
