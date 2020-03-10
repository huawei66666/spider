package com.huawei.spider.center.utils;

public class ThreadJoinTest2 {
    public static void main(String[] args) {
        try {
            MyThread t1 = new MyThread();
            t1.start();
            t1.join();

            for (int i = 0; i < 100; i++) {
                System.out.println("主线程：" + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class MyThread extends Thread {
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println("线程一：" + i);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
