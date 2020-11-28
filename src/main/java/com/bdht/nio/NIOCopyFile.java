package com.bdht.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class NIOCopyFile {
    public static void main(String[] args) throws Exception {
        //创建文件、流、通道
        File source = new File("D:\\temp\\1.jpg");
        FileInputStream fileInputStream = new FileInputStream(source);
        FileChannel sourceChannel = fileInputStream.getChannel();

        File target = new File("D:\\temp\\2.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream(target);
        FileChannel targetChannel = fileOutputStream.getChannel();

        //使用自带api拷贝文件
        targetChannel.transferFrom(sourceChannel, 0, source.length());

        //释放资源
        sourceChannel.close();
        targetChannel.close();
        fileInputStream.close();
        fileOutputStream.close();


    }
}
