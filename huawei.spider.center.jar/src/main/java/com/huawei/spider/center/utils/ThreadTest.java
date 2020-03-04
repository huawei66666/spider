package com.huawei.spider.center.utils;

public class ThreadTest extends Thread {

    public void run() {
        System.out.println("线程开启！");
    }

    public static void main(String[] args) {
        System.out.println("主方法执行...");

        ThreadTest threadTest = new ThreadTest();
        threadTest.start();
    }


}
