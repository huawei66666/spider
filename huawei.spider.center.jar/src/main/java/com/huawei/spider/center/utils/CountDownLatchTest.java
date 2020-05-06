package com.huawei.spider.center.utils;

import java.util.concurrent.CountDownLatch;

/**
 * 门闩，在门闩未开放时等待，能够保证线程的前后顺序，可以和锁混合使用，或者替代锁的功能
 */
public class CountDownLatchTest {

    CountDownLatch latch = new CountDownLatch(5);// 门上创建了5个门闩

    public void m1() {
        try {
            latch.await();// 等待所有门闩释放
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("门闩已全部释放！");
    }

    public void m2() {
        for (int i = 0; i < 10; i++) {
            if (latch.getCount() != 0) {
                System.out.println("门闩数量：" + latch.getCount());
                latch.countDown();// 减少门闩数量，每调用一次减少一个门闩
            }
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("m2() method" + i);
        }
    }

    public static void main(String[] args) {
        CountDownLatchTest test = new CountDownLatchTest();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                test.m1();
            }
        });
        t1.start();

        test.m2();
    }

}
