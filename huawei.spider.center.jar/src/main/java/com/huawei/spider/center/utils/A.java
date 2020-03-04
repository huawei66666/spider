package com.huawei.spider.center.utils;

import java.util.ArrayList;
import java.util.List;

public class A<T extends List> {

    public static void method(A<? extends List> a) {

    }

    public static void main(String[] args) {
        A a = new A();
        method(a);


    }


}
