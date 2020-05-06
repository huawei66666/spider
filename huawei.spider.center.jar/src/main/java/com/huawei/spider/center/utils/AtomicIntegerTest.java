package com.huawei.spider.center.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子类型，底层通过CAS实现线程的同步，使用的是乐观锁
 * 程序最终的执行结果是100000，是正确的
 */
public class AtomicIntegerTest {

    AtomicInteger a = new AtomicInteger(0);

    public void add() {
        for (int i = 0; i < 10000; i++) {
            a.incrementAndGet();
        }
    }

    public static void main(String[] args) {
        AtomicIntegerTest test = new AtomicIntegerTest();
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    test.add();
                }
            }));
        }
        for (Thread t : list) {
            t.start();
        }
        for (Thread t : list) {
            try {
                t.join();// 等待所有线程执行完成后合并到主线程当前位置来，再往下执行
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(test.a.intValue());
    }

}
