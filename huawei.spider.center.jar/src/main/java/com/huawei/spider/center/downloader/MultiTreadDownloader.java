package com.huawei.spider.center.downloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 功能：多线程下载器
 * 作者：laihuawei(laihuawei@lianj.com)
 * 日期：2018年09月2018/9/7日 15:38
 * 版权所有：广东联结网络技术有限公司 版权所有(C)
 */
public class MultiTreadDownloader {

    private Logger logger = LoggerFactory.getLogger(MultiTreadDownloader.class);

    /**
     * 定义下载资源的路径
     */
    private String path;
    /**
     * 指定所下载的文件的保存位置
     */
    private String targetFile;
    /**
     * 定义需要使用多少线程下载资源
     */
    private int threadNum;
    /**
     * 定义下载的线程对象
     */
    private DownThread[] threads;
    /**
     * 定义下载的文件的总大小
     */
    private int fileSize;

    /**
     * 构造器
     *
     * @param path
     * @param targetFile
     * @param threadNum
     */
    public MultiTreadDownloader(String path, String targetFile, int threadNum) {
        this.path = path;
        this.threadNum = threadNum;
        threads = new DownThread[threadNum];// 初始化threads数组
        this.targetFile = targetFile;
    }

    /**
     * 下载
     *
     * @throws Exception
     */
    public void download() throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty(
                "Accept",
                "image/gif, image/jpeg, image/pjpeg, image/pjpeg, "
                        + "application/x-shockwave-flash, application/xaml+xml, "
                        + "application/vnd.ms-xpsdocument, application/x-ms-xbap, "
                        + "application/x-ms-application, application/vnd.ms-excel, "
                        + "application/vnd.ms-powerpoint, application/msword, */*");
        conn.setRequestProperty("Accept-Language", "zh-CN");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("Connection", "Keep-Alive");

        // 得到文件大小
        fileSize = conn.getContentLength();
        conn.disconnect();
        int currentPartSize = fileSize / threadNum + 1;//这里不必一定要加1,不加1也可以
        RandomAccessFile file = new RandomAccessFile(targetFile, "rw");
        file.setLength(fileSize);// 设置本地文件的大小
        file.close();
        for (int i = 0; i < threadNum; i++) {
            int startPos = i * currentPartSize;// 计算每条线程的下载的开始位置
            RandomAccessFile currentPart = new RandomAccessFile(targetFile, "rw");// 每个线程使用一个RandomAccessFile进行下载
            currentPart.seek(startPos);// 定位该线程的下载位置
            threads[i] = new DownThread(startPos, currentPartSize, currentPart);// 创建下载线程
            threads[i].start();// 启动下载线程
        }
    }

    /**
     * 获取下载的完成百分比
     */
    public double getCompleteRate() {
        int sumSize = 0;// 统计多条线程已经下载的总大小
        for (int i = 0; i < threadNum; i++) {
            sumSize += threads[i].length;
        }
        return sumSize * 1.0 / fileSize; // 返回已经完成的百分比
    }

    /**
     * 下载线程内部类
     */
    private class DownThread extends Thread {
        /**
         * 当前线程的下载位置
         */
        private int startPos;
        /**
         * 定义当前线程负责下载的文件大小
         */
        private int currentPartSize;
        /**
         * 当前线程需要下载的文件块
         */
        private RandomAccessFile currentPart;
        /**
         * 定义已经该线程已下载的字节数
         */
        public int length;

        /**
         * 构造器
         *
         * @param startPos
         * @param currentPartSize
         * @param currentPart
         */
        public DownThread(int startPos, int currentPartSize, RandomAccessFile currentPart) {
            this.startPos = startPos;
            this.currentPartSize = currentPartSize;
            this.currentPart = currentPart;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5 * 1000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty(
                        "Accept",
                        "image/gif, image/jpeg, image/pjpeg, image/pjpeg, "
                                + "application/x-shockwave-flash, application/xaml+xml, "
                                + "application/vnd.ms-xpsdocument, application/x-ms-xbap, "
                                + "application/x-ms-application, application/vnd.ms-excel, "
                                + "application/vnd.ms-powerpoint, application/msword, */*");
                conn.setRequestProperty("Accept-Language", "zh-CN");
                conn.setRequestProperty("Charset", "UTF-8");
                InputStream inStream = conn.getInputStream();
                inStream.skip(this.startPos);// 跳过startPos个字节，表明该线程只下载自己负责哪部分文件。
                byte[] buffer = new byte[1024];
                int hasRead = 0;

                // 读取网络数据，并写入本地文件
                while (length < currentPartSize && (hasRead = inStream.read(buffer)) != -1) {
                    currentPart.write(buffer, 0, hasRead);
                    length += hasRead;// 累计该线程下载的总大小
                }
                currentPart.close();
                inStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
