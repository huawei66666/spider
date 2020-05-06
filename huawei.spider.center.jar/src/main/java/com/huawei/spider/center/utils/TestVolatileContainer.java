package com.huawei.spider.center.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 利用volatile线程的可见性特点，实现容器数据的监听，达到了线程通信的目的
 */
public class TestVolatileContainer {

    public static void main(String[] args) {
        Container container = new Container();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    container.add(new Object());
                    System.out.println("添加了：" + i);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Integer size = container.size();
                    if(size == 5) {
                        System.out.println("容量为5");
                        break;
                    }
                }
            }
        });
        t2.start();
    }

}


class Container {

    volatile List<Object> list = new ArrayList<>();

    public void add(Object o) {
        list.add(o);
    }

    public Integer size() {
        return list.size();
    }

}