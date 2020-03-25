package com.huawei.spider.center.utils;

public class InnerClassTest {

    private int a = 1;
    private static int b = 1;

    InnerClass1 innerClass1 = new InnerClass1();
    InnerClass2 innerClass2 = new InnerClass2();

    /**
     * 私有动态内部类
     */
    private class InnerClass1 {
        public void m() {
            System.out.println("内部类访问外部类成员：" + a);
        }

        // 不能定义主方法
//            public static void main(String[] args) {
//
//            }
    }

    /**
     * 静态内部类（类的静态属性）
     */
    public static class InnerClass2 {
        public void m1() {
            System.out.println("我是内部类方法1");
        }

        public void m2() {
            System.out.println("内部类访问外部类成员：" + b);
        }

        public static void main(String[] args) {

        }
    }

    public void test1() throws Exception {
        InnerClass1 innerClass5 = new InnerClass1();
    }

    public void test2() throws Exception {
        InnerClass2 innerClass6 = new InnerClass2();
    }

    public void test3() throws Exception {
        // 局部内部类
        class InnerClass3 {
            public void in() {
            }

            // 不能定义主方法
//            public static void main(String[] args) {
//
//            }
        }
//        static class InnerClass4 {// 动态方法不能定义静态局部内部类
//
//        }
    }

    public static void main(String[] args) {

        // 在静态方法中成员内部类必须先获取外部类引用，然后out.new InnerClass1()的方式创建对象
        InnerClassTest out = new InnerClassTest();
        InnerClassTest.InnerClass1 innerClass3 = out.new InnerClass1();// 实例内部类对象

        // 在静态方法中，静态内部类可直接创建对象
        InnerClass2 innerClass4 = new InnerClass2();

    }
}
