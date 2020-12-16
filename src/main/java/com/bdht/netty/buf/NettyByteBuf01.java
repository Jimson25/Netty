package com.bdht.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyByteBuf01 {
    public static void main(String[] args) {
        /**
         * ByteBuf是netty自带的一个buffer对象
         *  其底层维护的是一个byte数组，有三个属性分别是readIndex、writeIndex、capacity
         *  通过这三个属性将byteBuf分成三个部分
         *  0-readIndex：已读区域
         *  readIndex-writeIndex：可读区域
         *  writeIndex-capacity：可写区域
         */

        ByteBuf byteBuf = Unpooled.buffer(16);

        for (int i = 0; i < 16; i++) {
            byteBuf.writeByte(i);
        }

        System.out.println(byteBuf.capacity());

        for (int i = 0; i < byteBuf.capacity(); i++) {
            //readByte方法调用后会将readIndex下标往后移一位
            System.out.println(byteBuf.readByte());
        }

        //重置readIndex下标，如果这里没有调用，那么下面的代码会抛出异常
        byteBuf.resetReaderIndex();

        for (int i = 0; i < byteBuf.capacity(); i++) {
            System.out.println(byteBuf.readByte());
        }


    }
}
