package com.bdht.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 还是存在bug
 */
public class NIOFileChannel {
    public static void main(String[] args) throws Exception {
        fileWrite();
        fileRead();

    }

    private static void fileRead() throws IOException {
        //创建文件对象
        File file = new File("d:\\temp\\1.txt");
        //从文件对象获取输入流
        FileInputStream fileInputStream = new FileInputStream(file);
        //获取一个channel
        FileChannel fileChannel = fileInputStream.getChannel();
        //创建一个buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //使用channel将数据读取到buffer中，返回读取到的数据的长度
        while (fileChannel.read(byteBuffer) != -1) {
            System.out.println(new String(byteBuffer.array()));
            byteBuffer.clear();
        }

    }


    private static void fileWrite() throws IOException, InterruptedException {
//        String str = "hello world!";
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < 5000; i++) {
            str.append("hello 哈哈哈哈 ");
        }

        //获取一个文件输出流
        FileOutputStream fileOutputStream = new FileOutputStream("d:\\temp\\1.txt");
        //从输出流获取一个Channel
        FileChannel fileChannel = fileOutputStream.getChannel();
        //创建一个buffer
        int capacity = 1024;
        ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
        //将字符串放入到buffer
        //定义读数据的初始位置
        int index = 0;
        //定义一次读取的长度
        int len = capacity;
        //通过字符串获取byte数组
        byte[] bytes = str.toString().getBytes();
        while (true) {
            //将字符数组中的(index，index+len)位置的数据读取到缓冲区
            byteBuffer.put(bytes, index, len);
            //将读取的起始位置移动len位
            index += len;
            //如果index+len超出了数组的长度，下次就只读取到数组最后一位
            if ((index + len) >= bytes.length) {
                len = bytes.length - index;
            }
            //对bytebuffer进行翻转-》写入数据完成后position指向最后一个位置，下次要开始读需要将position指向第一个位置
            byteBuffer.flip();
            //将buffer放入到Channel中
            if (fileChannel.write(byteBuffer) < capacity) {
                break;
            }
            byteBuffer.clear();
        }
        fileOutputStream.close();
    }
}
