package com.huawei.spider.center.thread;

import java.text.DecimalFormat;
import java.util.concurrent.Future;

/**
 * Created by huawei on 2018/9/18.
 */
public class RateTask implements Runnable {

    /**
     * 当前下载文件名称
     */
    private String filename;

    /**
     * 文件总大小
     */
    private int fileSize;

    /**
     * 当前下载量
     */
    private int downloadSize;

    /**
     * 当前任务调度器
     */
    private Future future;

    public RateTask() {
    }

    /**
     * 构造函数
     * @param filename
     * @param fileSize
     * @param downloadSize
     * @param future
     */
    public RateTask(String filename, int fileSize, int downloadSize, Future future) {
        this.filename = filename;
        this.fileSize = fileSize;
        this.downloadSize = downloadSize;
        this.future = future;
    }

    @Override
    public void run() {
        if (downloadSize < fileSize) {
            DecimalFormat df = new DecimalFormat("0.00");
            double rate = (downloadSize / fileSize) * 100;
            String rates = df.format(rate) + "%";
            System.out.println(String.format("文件" + filename + " 已下载：" + rates));
        }
        if (downloadSize == fileSize) {
            System.out.println(filename + "下載完成！");
            future.cancel(true);
        }
    }
}
