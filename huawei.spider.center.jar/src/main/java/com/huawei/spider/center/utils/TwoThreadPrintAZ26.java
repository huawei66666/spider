package com.huawei.spider.center.utils;

public class TwoThreadPrintAZ26 {

    public static void main(String[] args) {
        TwoThreadPrintAZ26 twoThreadPrintAZ26 = new TwoThreadPrintAZ26();
        Thread t1 = new Thread(new ThreadPrintAZ(twoThreadPrintAZ26));
        Thread t2 = new Thread(new ThreadPrint26(twoThreadPrintAZ26));
        t1.start();
        System.out.println("线程一已开启");
        t2.start();
        System.out.println("线程二已开启");
    }



}
