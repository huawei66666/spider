package com.huawei.spider.center.utils;

public class ProducerConsumer {

    public static void main(String[] args) {
//        HamburgerStack hamburgerStack = new HamburgerStack();
//        Producer producer = new Producer(hamburgerStack);
//        Consumer consumer = new Consumer(hamburgerStack);
//        Thread t1 = new Thread(producer);
//        Thread t2 = new Thread(consumer);
//        t1.start();
//        t2.start();

        try {
            int a = 19;
            int b =  a / 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("抛异常后执行了！");
    }
}
