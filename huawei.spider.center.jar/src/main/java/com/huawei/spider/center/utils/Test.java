package com.huawei.spider.center.utils;

public class Test {
    private void test1() {
        System.out.println("private");
    }
    protected void test2() {
        System.out.println("protected");
    }

    public static void main(String[] args) {
        FinalTest test1 = new FinalTest();
        FinalTest test2 = new FinalTest();
        System.out.println(test1.B);
        System.out.println(test2.A);
    }

}
