package com.bdht.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {
    public static void main(String[] args) throws Exception {
        //创建一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置通道为非阻塞
        socketChannel.configureBlocking(false);
        //设置服务器的网络地址和端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 8888);

        if (!socketChannel.connect(inetSocketAddress)){
            while (!socketChannel.finishConnect()){
                System.out.println("等待连接～～");
            }
        }

        ByteBuffer buffer = ByteBuffer.wrap("hello world".getBytes());
        socketChannel.write(buffer);
        System.in.read();
    }
}
