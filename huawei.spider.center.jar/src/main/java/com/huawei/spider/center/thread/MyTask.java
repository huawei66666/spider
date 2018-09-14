package com.huawei.spider.center.thread;

/**
 * 功能：测试多线程类
 * 作者：laihuawei(laihuawei@lianj.com)
 * 日期：2018年09月2018/9/14日 11:44
 * 版权所有：广东联结网络技术有限公司 版权所有(C)
 */
public class MyTask implements Runnable {

    private int taskNum;

    public MyTask(int num) {
        this.taskNum = num;
    }

    @Override
    public void run() {
        System.out.println("正在执行task " + taskNum);
        try {
            Thread.currentThread().sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task " + taskNum + "执行完毕");
    }

}
