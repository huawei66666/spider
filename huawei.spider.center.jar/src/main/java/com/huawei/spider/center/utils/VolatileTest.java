package com.huawei.spider.center.utils;

import java.util.concurrent.TimeUnit;

/**
 * volatile的特点：
 * 1.保持线程操作的可见性
 * 2.禁止指令重排序
 * 3.不能保证原子性
 */
public class VolatileTest {

    volatile boolean flag = true;// 如果未加volatile修饰，flag对其他线程是不可见的，即使主线程对其值进行了修改，对于t线程是不可见的

    public void m() {
        System.out.println("start...");
        while(flag) {}
        System.out.println("end...");
    }

    public static void main(String[] args) {
        VolatileTest test = new VolatileTest();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                test.m();
            }
        });
        t.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        test.flag = false;

    }
}
