package com.huawei.spider.center.utils;

public class ThreadSafeTest implements Runnable {

    int num = 10;

    @Override
    public void run() {
        synchronized ("") {
            while (num > 0) {
                try {
                    Thread.sleep(10);
                    System.out.println("tickets: " + num--);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ThreadSafeTest test = new ThreadSafeTest();
        Thread t1 = new Thread(test);
        Thread t2 = new Thread(test);
        Thread t3 = new Thread(test);
        Thread t4 = new Thread(test);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

}
