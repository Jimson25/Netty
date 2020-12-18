package com.bdht.netty.protocol;

//数据协议
public class MessageProtocol {
    //数据长度
    private int len;
    //内容
    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
