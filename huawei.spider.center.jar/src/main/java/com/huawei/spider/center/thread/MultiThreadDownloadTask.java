package com.huawei.spider.center.thread;

import com.huawei.spider.center.downloader.http.MultiTreadHttpDownloader;
import com.huawei.spider.center.downloader.http.MultiTreadHttpDownloader2;

/**
 * 功能：下载线程任务
 * 作者：laihuawei(laihuawei@lianj.com)
 * 日期：2018年09月2018/9/13日 17:40
 * 版权所有：广东联结网络技术有限公司 版权所有(C)
 */
public class MultiThreadDownloadTask implements Runnable {

    private int taskNum;// 当前线程
    private String name;// 下载资源名称
    private String url;// 下载资源地址
    private String outputPath;// 存放路径

    public MultiThreadDownloadTask() {
    }

    public MultiThreadDownloadTask(int num, String name, String url, String outputPath) {
        this.taskNum = num;
        this.name = name;
        this.url = url;
        this.outputPath = outputPath;
    }

    @Override
    public void run() {
        try {
            System.out.println("线程" + taskNum + "正在下载：" + name);

            MultiTreadHttpDownloader2 downloader = new MultiTreadHttpDownloader2(url, outputPath, 3);
            downloader.download();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (downloader.getCompleteRate() < 1) {
                        try {
                            System.out.println(name + "已完成:" + downloader.getCompleteRate());
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

            System.out.println("线程 " + taskNum + "  " + name + "下载完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
