package com.bdht.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * TextWebSocketFrame:表示一个文本帧
 */
public class MyWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("服务器端收到消息 " + msg.text());

        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间： " + LocalDateTime.now() + "消息： " + msg.text()));

    }

    /**
     * 当客户端连接后就会触发这个方法
     *
     * @param ctx 上下文
     * @throws Exception 异常信息
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("HandlerAdded 被调用 - " + ctx.channel().id().asLongText());
        System.out.println(ctx.channel().remoteAddress()+" --- 连接到服务器");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved 被调用 - " + ctx.channel().id().asLongText());
        System.out.println(ctx.channel().remoteAddress() + " --- 断开连接");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
        ctx.close();
    }
}
