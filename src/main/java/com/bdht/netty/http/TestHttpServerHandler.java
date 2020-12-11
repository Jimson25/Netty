package com.bdht.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * 1. SimpleChannelInboundHandler 继承了 ChannelInboundHandlerAdapter
 * 2. HttpObject客户端和服务端相互通讯的数据被封装成HttpObject
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    //channelRead0 读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {
            System.out.println("msg 类型： " + msg.getClass());
            System.out.println("客户端地址： " + ctx.channel().remoteAddress());

            //使用HTTP协议给客户端发送数据
            ByteBuf content = Unpooled.copiedBuffer("hello client;  ----from server", CharsetUtil.UTF_8);

            //构造一个HTTP响应（HttpResponse）
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);

            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());

            //将构造好的response返回
            ctx.writeAndFlush(response);

        }

    }
}
