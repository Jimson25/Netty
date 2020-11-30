package com.bdht.nio;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args) throws Exception {
        //开启一个serverSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //开启一个Selector
        Selector selector = Selector.open();

        //将serverSocketChannel绑定到本地端口
        serverSocketChannel.bind(new InetSocketAddress(8888));

        //将ServerSocketChannel注册到selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //死循环
        while (true) {
            //等待1秒无连接就继续下一次循环
            if (selector.select(1000) == 0) {
                System.out.println("等待 1s 无连接");
                continue;
            }

            //如果有连接进来，就获所有取连接的selectionKey
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            //以迭代器的方式遍历selectionKey集合
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                //获取一个selectionKey
                SelectionKey selectionKey = iterator.next();

                //如果这个selectionKey触发的事件是accept
                if (selectionKey.isAcceptable()) {
                    //通过serverSocketChannel获取一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //将获取到的socketChannel注册到Selector，同时提供一个ByteBuffer
                    socketChannel.register(selector, SelectionKey.OP_ACCEPT, ByteBuffer.allocate(1024));
                }
                if (selectionKey.isReadable()) {
                    //如果当前selectionKey触发read事件，就根据selectionKey获取到它对应的SocketChannel
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    // 根据selectionKey获取到对应的ByteBuffer
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    //将channel中的数据读取到buffer中
                    channel.read(byteBuffer);
                    //打印Buffer中的数据
                    System.out.println(new String(byteBuffer.array()));
                }
                //从集合中删除这个selectionKey，避免重复操作
                iterator.remove();
            }
        }
    }
}
