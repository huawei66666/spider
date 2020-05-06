package com.huawei.spider.center.utils;

import java.util.ArrayList;
import java.util.List;

public class Test {
    private void test1() {
        System.out.println("private");
    }
    protected void test2() {
        System.out.println("protected");
    }

    public static void main(String[] args) {
//        FinalTest test1 = new FinalTest();
//        FinalTest test2 = new FinalTest();
//        System.out.println(test1.B);
//        System.out.println(test2.A);

//        int a = 10;
//        add(a);
//        System.out.println(a);

        Test t = new Son();
        t.m1();

        List list = new ArrayList<>();

    }

    public static void add(int a) {
        System.out.println("我是父类的方法");
    }

    public void m1() {
        System.out.println("我是父类的方法");
    }
}

class Son extends Test {
    public void m1() {
        System.out.println("子类的方法");
    }
}
