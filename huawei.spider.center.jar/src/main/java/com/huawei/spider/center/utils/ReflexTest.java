package com.huawei.spider.center.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflexTest {

    public static void main(String[] args) {
        try {
            Reflex instance = new Reflex();
            Constructor[] constructors = instance.getClass().getDeclaredConstructors();
            if (constructors != null && constructors.length > 0) {
                for (Constructor c : constructors) {
                    System.out.println("构造方法：" + c.getName());
                }
            }

            Field[] fileds = instance.getClass().getDeclaredFields();
            if (fileds != null && fileds.length > 0) {
                for (Field f : fileds) {
                    System.out.println("字段名称：" + f.getName() + "字段类型：" + f.getType());
                }
            }

            Method[] methods = instance.getClass().getDeclaredMethods();
            if (methods != null && methods.length > 0) {
                for (Method m : methods) {
                    System.out.println("方法名称：" + m.getName());
                    Class<?>[] types = m.getParameterTypes();
                    for (Class c : types) {
                        System.out.println("方法类型名称：：" + c.getTypeName());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
