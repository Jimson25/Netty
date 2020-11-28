package com.bdht.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannelPlus {
    public static void main(String[] args) throws Exception {
        //创建文件、获取流、开启通道
        File file1 = new File("D:\\temp\\1.txt");
        FileInputStream fileInputStream = new FileInputStream(file1);
        FileChannel inputStreamChannel = fileInputStream.getChannel();

        //创建文件、获取流、开启通道
        File file2 = new File("D:\\temp\\2.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        FileChannel outputStreamChannel = fileOutputStream.getChannel();

        //创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(8192);

        while (true) {
            //复位缓冲区
            byteBuffer.clear();
            //从buffer中读取数据到通道
            int read = inputStreamChannel.read(byteBuffer);
            //读取到-1说明读完了
            if (read == -1) {
                break;
            }
            //切换通道状态，将其转换为写状态
            byteBuffer.flip();
            //将通道的数据写入到文件中
            outputStreamChannel.write(byteBuffer);

        }

        //关闭流
        fileInputStream.close();
        fileOutputStream.close();

    }
}
