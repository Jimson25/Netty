package com.bdht.nio;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * buffer的分散和聚集：
 * Scattering：分散，将数据写入到buffer时可以采用buffer数组依次写入
 * Gathering：聚集，从buffer中读取数据时，可以采用buffer数组依次读取
 */
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws Exception {
        //创建一个serverSocketChannel和Socket网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress socketAddress = new InetSocketAddress(8888);
        serverSocketChannel.socket().bind(socketAddress);

        //创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(5);

        //等待客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();
        int massageLength = 1;

        while (true) {
            long readByte = 0;
            /**
             *  视频里面这里加了个循环，但是感觉没啥用，
             *  在读取完依次数据时，如果独到的数据小于byteBuffer数组的最大容量，就依次读完
             *  如果读到的数据大于byteBuffer数组的最大容量，会先把读到的数据返回到客户端，
             *  再在下一次循环中把剩下的数据读取出来并写入到客户端
             */
//            while (readByte < 1) {
            //将数据读到buffes中
            readByte = socketChannel.read(byteBuffers);
//                System.out.println("byteRead: " + (readByte += read));
            Arrays.stream(byteBuffers).map(byteBuffer ->
                    "position= " + byteBuffer.position() +
                            ", limit: " + byteBuffer.limit()).forEach(System.out::println);
//            }

            //将buffers的状态翻转 读->写
            Arrays.asList(byteBuffers).forEach(Buffer::flip);

            long writeByte = 0;

            /**
             * 视频中这里也有一个循环，但是感觉可以不要，理由同上
             */
//            while (writeByte < 1) {
            //将bytebuffers中的数据写入到通道中
            writeByte = socketChannel.write(byteBuffers);
//                writeByte += write;
//            }

            Arrays.asList(byteBuffers).forEach(Buffer::clear);

            System.out.println("readByte: " + readByte + " writeByte: " + writeByte + " ,massageLength: " + massageLength);

        }


    }


}
