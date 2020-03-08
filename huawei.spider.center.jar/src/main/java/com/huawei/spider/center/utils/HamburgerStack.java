package com.huawei.spider.center.utils;

public class HamburgerStack {
    int index = 0;
    Hamburger[] hamburgers = new Hamburger[6];

    public synchronized void push(Hamburger hamburger) {
        if(index == hamburgers.length) {
            try {
                this.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.notify();
        hamburgers[index] = hamburger;
        index++;
    }

    public synchronized Hamburger pop() {
        if(index == 0) {
            try {
                this.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.notify();
        return hamburgers[--index];
    }
}






