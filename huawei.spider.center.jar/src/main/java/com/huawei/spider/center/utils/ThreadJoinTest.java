package com.huawei.spider.center.utils;

public class ThreadJoinTest {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                Thread t2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean flag = true;
                        int a = 0;
                        while (flag) {
                            System.out.println("线程二执行: " + a);
                            a++;
                            if (a >= 50) {
                                flag = false;
                                System.out.println("线程二结束！");
                            }
                        }
                    }
                });
                t2.start();

                boolean flag = true;
                int a = 0;
                while (flag) {
                    System.out.println("线程一执行: " + a);
                    a++;

                    if (a == 50) {
                        try {
                            t2.join();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (a >= 100) {
                        flag = false;
                        System.out.println("线程一结束！");
                    }
                }
            }
        });
        t1.start();

    }
}
