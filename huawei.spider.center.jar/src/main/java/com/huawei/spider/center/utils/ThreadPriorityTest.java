package com.huawei.spider.center.utils;

public class ThreadPriorityTest {

    public static void main(String[] args) {
        Thread t1 = new Thread(new MyThreadTest());
        Thread t2 = new Thread(new MyThreadTest2());
        t1.setPriority(Thread.MAX_PRIORITY);
        t1.start();
        t2.start();
//        for (int i = 0; i < 20; i++) {
//            System.out.println("主线程：" + i);
//        }
    }
}

class MyThreadTest implements Runnable {
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("线程一：" + i);
        }
    }
}

class MyThreadTest2 implements Runnable {
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("线程二：" + i);
        }
    }
}

