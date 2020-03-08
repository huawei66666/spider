package com.huawei.spider.center.utils;

public class TheadDeadLock implements Runnable {

    int flag = 1;

    static Object o1 = new Object();
    static Object o2 = new Object();

    @Override
    public void run() {
        System.out.println("flag: " + flag);
        if (flag == 1) {
            synchronized (o1) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (o2) {
                    System.out.println("线程1执行完了！");
                }
            }
        }

        if (flag == 0) {
            synchronized (o2) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (o1) {
                    System.out.println("线程2执行完了！");
                }
            }
        }
    }

    public static void main(String[] args) {
        TheadDeadLock deadLock1 = new TheadDeadLock();
        TheadDeadLock deadLock2 = new TheadDeadLock();
        deadLock1.flag = 1;
        deadLock2.flag = 0;
        Thread t1 = new Thread(deadLock1);
        Thread t2 = new Thread(deadLock2);
        t1.start();
        t2.start();
    }

}
