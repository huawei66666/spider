package com.huawei.spider.center.utils;

public class Reflex {

    public static int a = 1;
    public String s = "ssss";

    public Reflex() {

    }

    private Reflex(int a, String b) {
        this.a = a;
        this.s = s;
    }

    public static int getA() {
        return a;
    }

    public static void setA(int a) {
        Reflex.a = a;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }
}
