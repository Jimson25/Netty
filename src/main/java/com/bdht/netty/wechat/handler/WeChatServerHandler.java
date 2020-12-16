package com.bdht.netty.wechat.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeChatServerHandler extends SimpleChannelInboundHandler<String> {

    //定义一个channelGroup管理所有channel
    private static DefaultChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //表示连接被建立，当连接建立后第一个执行这个方法
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //该方法会将channelGroup中所有的channel遍历并发送传入的消息
        channelGroup.writeAndFlush(format.format(new Date()) + " - " + "客户端：" + channel.remoteAddress() + " 加入群聊");
        //将新上线的channel加入到group中
        channelGroup.add(channel);
    }

    //当某个channel中断连接时触发
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(format.format(new Date()) + " - " + "客户端：" + channel.remoteAddress() + " 离开群聊");
        System.out.println("channelGroup size : " + channelGroup.size());
    }

    //当某个channel处于活动状态，提示上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(format.format(new Date()) + " - " + ctx.channel().remoteAddress() + "上线");
    }

    //当某个channel处于离线状态时触发
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(format.format(new Date()) + " - " + ctx.channel().remoteAddress() + "离线");
    }

    //读取数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        Date date = new Date();
        System.out.println(format.format(date) + " - " + "用户： " + channel.remoteAddress() + " 发送消息： " + msg);
        channelGroup.forEach(ch -> {
            if (channel != ch) {
                //不是当前发消息的客户端
                ch.writeAndFlush(format.format(date) + " - " + "用户： " + channel.remoteAddress() + " 发送消息： " + msg);
            } else {
                ch.writeAndFlush(format.format(date) + " - " + "本机： " + channel.remoteAddress() + " 发送消息： " + msg);
            }

        });

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println(s + " - " + cause.getMessage());
    }
}
