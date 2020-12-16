package com.bdht.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

public class NettyByteBuf02 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("HelloWorld", StandardCharsets.UTF_8);
        //使用相关的方法
        if (byteBuf.hasArray()) { // true

            byte[] content = byteBuf.array();

            //将 content 转成字符串
            System.out.println(new String(content, StandardCharsets.UTF_8));

            System.out.println("byteBuf=" + byteBuf);

            System.out.println(byteBuf.arrayOffset()); // 0
            System.out.println(byteBuf.readerIndex()); // 0
            System.out.println(byteBuf.writerIndex()); // 11
            System.out.println(byteBuf.capacity()); // 64

//            //System.out.println(byteBuf.readByte()); //
//            System.out.println(byteBuf.getByte(0)); // 104
//
//            int len = byteBuf.readableBytes(); //可读的字节数  12
//            System.out.println("len=" + len);
//
//            //使用for取出各个字节
//            for (int i = 0; i < len; i++) {
//                System.out.println((char) byteBuf.getByte(i));
//            }
//
//            //按照某个范围读取
//            System.out.println(byteBuf.getCharSequence(0, 4, Charset.forName("utf-8")));
//            System.out.println(byteBuf.getCharSequence(4, 6, Charset.forName("utf-8")));


        }

    }
}
