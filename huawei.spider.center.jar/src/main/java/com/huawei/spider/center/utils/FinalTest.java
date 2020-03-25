package com.huawei.spider.center.utils;

public final class FinalTest {

    public static final int A = 0;

    public final int B = 1;

    public final void m1() {

    }

    public static void main(String[] args) {
        FinalTest test1 = new FinalTest();
        FinalTest test2 = new FinalTest();
        System.out.println(test1.B);
        System.out.println(test2.A);
    }
}
