package com.huawei.spider.center.utils;

public @interface AnnotationTest {
    String value();
    Class type() default void.class;
}
