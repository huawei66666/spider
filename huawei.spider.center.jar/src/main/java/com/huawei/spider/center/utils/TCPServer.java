package com.huawei.spider.center.utils;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(6666);
            while (true) {
                Socket s = ss.accept();
                System.out.println("A client connected!");
                DataInputStream ios = new DataInputStream(s.getInputStream());
                String content = ios.readUTF();
                System.out.println(content);
                ios.close();
                s.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
