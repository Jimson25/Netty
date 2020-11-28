package com.bdht.nio;


import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * mappedByteBuffer可以让文件直接在内存中（堆外内存）做修改
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws Exception{
        RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\temp\\1.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();

        /**
         * FileChannel.MapMode.READ_WRITE: 文件模式，治理设置模式为读写模式
         * 0: 设置起始位置为0
         * 5: 设置一次只能读取5个字节
         */
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        map.put(0, (byte) 'U');
        randomAccessFile.close();


    }
}
