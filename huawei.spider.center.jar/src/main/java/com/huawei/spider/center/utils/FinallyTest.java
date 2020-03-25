package com.huawei.spider.center.utils;

public class FinallyTest {

    public int a = 0;

    /**
     * try或者catch即使进行了return，finally依然会执行；
     * finally语句在try和catch语句中的return执行后、返回前执行；
     * 若finally语句中没有return，则其执行结果不影响try和catch中已确定的返回值；
     * 若finally语句中有return，则其执行后的结果会直接返回。
     *
     * @return
     */
    public int test() {
        try {
            return this.a;
        } catch (Exception e) {
            return this.a;
        } finally {
            this.a = 10;
            System.out.println("finally执行了！");
//            return this.a;
        }
    }

    public static void main(String[] args) {
        FinallyTest t = new FinallyTest();
        System.out.println("调用之前：" + t.a);
        int r = t.test();
        System.out.println("==== 主函数执行打印： ===>> " + r);
        System.out.println("调用之后：" + t.a);
    }
}
