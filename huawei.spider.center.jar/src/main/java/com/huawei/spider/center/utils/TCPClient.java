package com.huawei.spider.center.utils;

import java.io.DataOutputStream;
import java.net.Socket;

public class TCPClient {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("127.0.0.1", 6666);
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeUTF("Hello Server!");
            dos.flush();
            dos.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
