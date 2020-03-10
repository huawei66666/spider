package com.huawei.spider.center.utils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
    public static void main(String[] args) {
        try {
            DatagramSocket s = new DatagramSocket(5678);
            byte[] bytes = new byte[1024];
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
            while (true) {
                s.receive(packet);
                String content = new String(bytes, 0, packet.getLength());
                System.out.println(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
