package com.huawei.spider.center.utils;

/**
 * 最先加载bootstrap classloader，由它加载其他的classloader，再由其他的classloader加载类class
 */
public class ClassLoaderTest {
    public static void main(String[] args) {
        System.out.println(String.class.getClassLoader());
        System.out.println(ClassLoaderTest.class.getClassLoader().getClass().getName());
    }
}
