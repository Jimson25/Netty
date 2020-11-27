package com.bdht.nio;

import java.nio.Buffer;
import java.nio.IntBuffer;

public class BasicBuffer {
    public static void main(String[] args) {
        //创建一个buffer
        IntBuffer intBuffer = IntBuffer.allocate(5);

        //向buffer中放数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
            showMsg(intBuffer);
        }
        //从buffer中取数据，下面这行代码会将buffer从写状态转换为读状态
        intBuffer.flip();
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
        showMsg(intBuffer);
        intBuffer.clear();
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }

    public static void showMsg(Buffer buffer) {
        System.out.println("capacity: " + buffer.capacity());
        System.out.println("position: " + buffer.position());
        System.out.println("limit: " + buffer.limit());

    }
}
