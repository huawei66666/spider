package com.huawei.spider.center.utils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁，类似于syncronized锁，可重入
 * 所谓的可重入，就是说在同步方法里面调用了当前对象另外一个同步方法或者父类的方法，
 * 并且另外的同步方法的锁对象与当前的同步方法相同，那么
 * 在进入另外一个同步方法时当前的线程不用进行锁竞争，可直接进入
 *
 * 运行结果：当m1执行完成之后m2才会执行
 *
 */
public class ReentrantLockTest {

    Lock lock = new ReentrantLock();

    public void m1() {
        try {
            lock.lock();
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("m1 method " + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void m2() {
        lock.lock();
        System.out.println("m2 method...");
        lock.unlock();
    }


    /*public void m2() {
        boolean flag = false;
        try {
            flag = lock.tryLock(5, TimeUnit.SECONDS);
            if(flag) {
                System.out.println("已释放锁");
            } else {
                System.out.println("未释放锁");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(flag) {
                lock.unlock();
            }
        }
    }*/

    public static void main(String[] args) {
        ReentrantLockTest test = new ReentrantLockTest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                test.m1();
            }
        }).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                test.m2();
            }
        }).start();

    }

}
