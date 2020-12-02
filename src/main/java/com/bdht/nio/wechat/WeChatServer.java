package com.bdht.nio.wechat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class WeChatServer {
    private static Selector selector;
    private static ServerSocketChannel listenChannel;
    private static final int PORT = 8888;

    private WeChatServer() {
        try {
            //得到一个选择器
            selector = Selector.open();
            //得到一个server socket channel
            listenChannel = ServerSocketChannel.open();
            //绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置为非阻塞
            listenChannel.configureBlocking(false);
            //将channel注册到selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listen() {
        while (true) {
            try {
                //2秒内有事件发生的通道的个数大于0
                if (selector.select(2000) > 0) {
                    //获取有事件发生的通道对应的selectionKey的集合并获取迭代器
                    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        //如果selectionKey对应的是连接事件
                        if (key.isAcceptable()) {
                            SocketChannel socketChannel = listenChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress() + " 上线~");
                        }
                        //selectionKey对应的是读的事件
                        if (key.isReadable()) {
                            //读取消息
                            read(key);
                        }
                        keyIterator.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //读取客户端消息
    private void read(SelectionKey key) {
        //从key获取channel
        SocketChannel channel = (SocketChannel) key.channel();
        //创建一个bytebuffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        try {
            int read = channel.read(buffer);
            if (read > 0) {
                String msg = new String(buffer.array());
                System.out.println("from 客户端： " + msg);
                //将读到的消息发送给其他客户端
                sendMsgToOtherClient(msg, key, selector);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + "离线~");
                key.cancel();
                channel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 向其他客户端发送消息
     *
     * @param msg      要发送的消息
     * @param from     发消息的那个客户端的channel对应的selectionKey
     * @param selector 发消息的客户端所注册的selector
     */
    private void sendMsgToOtherClient(String msg, SelectionKey from, Selector selector) {
        //通过selector获取selectionKey的集合
        Set<SelectionKey> keys = selector.keys();
        //从集合中删除发送消息的那个key
        keys.remove(from);
        //迭代器遍历出每个key
        Iterator<SelectionKey> iterator = keys.iterator();
        while (iterator.hasNext()) {
            //使用通过迭代器获取的selectionKey来获取它对应的channel
            SocketChannel targetChannel = (SocketChannel) iterator.next().channel();
            //将消息转发给其他客户端
            try {
                //将数据写入到对应的通道中
                targetChannel.write(ByteBuffer.wrap(msg.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void openServer() {
        new WeChatServer().listen();
    }


}
