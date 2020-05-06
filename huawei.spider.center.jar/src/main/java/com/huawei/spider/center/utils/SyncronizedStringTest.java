package com.huawei.spider.center.utils;

/**
 * 运行结果为只打印m1方法执行了，m2不会有执行的机会
 * 原因是synchronized在一个线程锁定了一个对象之后，
 * 另外的线程不能调用以该对象为锁对象的另外的synchronized
 * 方法，因为涉及到锁的竞争
 */
public class SyncronizedStringTest {

    public String s1 = "hello";
    public String s2 = "hello";// s1与s2其实为同一个对象，在常量池中

    public void m1() {
        synchronized (s1) {
            System.out.println("m1 start");
            while (true) {

            }
        }
    }

    public void m2() {
        synchronized (s2) {
            System.out.println("m2 start");
            while (true) {

            }
        }
    }

    public static void main(String[] args) {
        SyncronizedStringTest test = new SyncronizedStringTest();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                test.m1();
            }
        });
        t1.start();
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                test.m2();
            }
        });
        t2.start();
    }

}
