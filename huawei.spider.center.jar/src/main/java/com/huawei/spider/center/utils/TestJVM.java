package com.huawei.spider.center.utils;

import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class TestJVM {

    public static void main(String[] args) {
        List<GarbageCollectorMXBean> l = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean b : l) {
            System.out.println(b.getName());
        }
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
