package com.bdht.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {

    public static void main(String[] args) throws IOException {
        ExecutorService threadPool = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println(printThreadMsg(Thread.currentThread()) + "服务器在8888端口启动~");
        while (true) {
            System.out.println(printThreadMsg(Thread.currentThread()) + "等待连接~");
            //服务端会在这里阻塞
            Socket socket = serverSocket.accept();
            //只有当一个新的连接加入进来才会执行下面的代码
            threadPool.execute(() -> handler(socket));
        }


    }

    private static void handler(Socket socket) {
        byte[] bytes = new byte[1024];
        try {
            System.out.println(printThreadMsg(Thread.currentThread()) + "连接到服务器~");
            InputStream inputStream = socket.getInputStream();
            while (true) {
                int read = inputStream.read(bytes);
                if (read == -1) {
                    break;
                }
                System.out.println(printThreadMsg(Thread.currentThread()) + "接收到数据：" +
                        new String(bytes, 0, read, "GBK"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                System.out.println(printThreadMsg(Thread.currentThread()) + "连接断开~");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    
    public static String printThreadMsg(Thread thread) {
        return "thread-id: " + thread.getId() + " --- thread-name: " + thread.getName() + " --- ";
    }
}
