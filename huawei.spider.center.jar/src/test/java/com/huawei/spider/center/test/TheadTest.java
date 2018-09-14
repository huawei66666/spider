package com.huawei.spider.center.test;

import com.huawei.spider.center.thread.MyTask;
import com.huawei.spider.center.utils.ThreadHelper;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 功能：线程测试类
 * 作者：laihuawei(laihuawei@lianj.com)
 * 日期：2018年09月2018/9/7日 15:06
 * 版权所有：广东联结网络技术有限公司 版权所有(C)
 */
public class TheadTest {

    @Test
    public void testTheadHelper() throws Exception {
        /*for (int i = 0; i < 5; i++) {
            System.out.println("主线程: " + (i + 1) + "次执行");
            Thread.sleep(2000);
        }*/
        ThreadHelper.THREAD_POOL.submit(new Runnable() {
            @Override
            public void run() {
                for (int j = 0; j < 5; j++) {
                    try {
                        System.out.println("子线程执行：" + (j + 1) + "次");
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        System.out.println("线程中断");
                    }
                }
            }
        });

        Thread.sleep(10000);
    }

    @Test
    public void testExecutor() throws Exception {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5));
        for (int i = 0; i < 15; i++) {
            MyTask myTask = new MyTask(i);
            executor.execute(myTask);
            System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
                    executor.getQueue().size() + "，已执行玩别的任务数目：" + executor.getCompletedTaskCount());
        }
        executor.shutdown();
    }

}




