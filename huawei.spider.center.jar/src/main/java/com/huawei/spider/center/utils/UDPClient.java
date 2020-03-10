package com.huawei.spider.center.utils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class UDPClient {

    public static void main(String[] args) {
        try {
            DatagramSocket s = new DatagramSocket(8858);
            Long l = 1233333L;
            byte[] buffer = ("Hello Server!" + l).getBytes();
            DatagramPacket dp = new DatagramPacket(buffer, buffer.length, new InetSocketAddress("127.0.0.1", 5678));
            s.send(dp);
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
