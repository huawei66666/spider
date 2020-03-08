package com.huawei.spider.center.utils;


public class Consumer implements Runnable {

    public HamburgerStack hamburgerStack;

    public Consumer(HamburgerStack hamburgerStack) {
        this.hamburgerStack = hamburgerStack;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            Hamburger hamburger = hamburgerStack.pop();
            System.out.println("消费者消费了一个汉堡：" + hamburger);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
