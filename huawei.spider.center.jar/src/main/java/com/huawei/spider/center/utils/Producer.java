package com.huawei.spider.center.utils;

public class Producer implements Runnable {

    public HamburgerStack hamburgerStack;

    public Producer(HamburgerStack hamburgerStack) {
        this.hamburgerStack = hamburgerStack;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            Hamburger hamburger = new Hamburger(i);
            hamburgerStack.push(hamburger);
            System.out.println("生产者生产了一个汉堡：" + i);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
