package com.huawei.spider.center.utils;

public class RunnableTest {

    public static void main(String[] args) {
        RunnableTest test = new RunnableTest();
        new Thread(new Runnable() {
            public void run() {
                System.out.println("runnable方式创建线程！");
            }
        });
    }


}
