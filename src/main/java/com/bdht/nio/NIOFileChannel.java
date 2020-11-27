package com.bdht.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel {
    public static void main(String[] args) throws IOException {
        String str = "hello world!";
        //获取一个文件输出流
        FileOutputStream fileOutputStream = new FileOutputStream("d:\\temp\\1.txt");
        //从输出流获取一个Channel
        FileChannel fileChannel = fileOutputStream.getChannel();
        //创建一个buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //将字符串放入到buffer
        byteBuffer.put(str.getBytes());
        //对bytebuffer进行翻转-》写入数据完成后position指向最后一个位置，下次要开始读需要将position指向第一个位置
        byteBuffer.flip();
        //将buffer放入到Channel中
        fileChannel.write(byteBuffer);
        fileOutputStream.close();


    }
}
