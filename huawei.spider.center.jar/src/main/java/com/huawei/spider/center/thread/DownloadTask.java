package com.huawei.spider.center.thread;

import com.huawei.spider.center.downloader.http.HttpDownloader;

/**
 * 功能：下载线程任务
 * 作者：laihuawei(laihuawei@lianj.com)
 * 日期：2018年09月2018/9/13日 17:40
 * 版权所有：广东联结网络技术有限公司 版权所有(C)
 */
public class DownloadTask implements Runnable {

    private int taskNum;// 当前线程
    private String name;// 下载资源名称
    private String url;// 下载资源地址
    private String outputPath;// 存放路径

    public DownloadTask() {
    }

    public DownloadTask(int num, String name, String url, String outputPath) {
        this.taskNum = num;
        this.name = name;
        this.url = url;
        this.outputPath = outputPath;
    }

    @Override
    public void run() {
        try {
            System.out.println("线程" + taskNum + "正在下载：" + name);
            HttpDownloader httpDownloader = new HttpDownloader();
            httpDownloader.download(url, outputPath, 30000);
            System.out.println("线程 " + taskNum + "下载完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
