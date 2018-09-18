package com.huawei.spider.center.downloader.http;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 功能：http、https 多线程下载器
 * 作者：laihuawei(laihuawei@lianj.com)
 * 日期：2018年09月2018/9/7日 15:38
 * 版权所有：广东联结网络技术有限公司 版权所有(C)
 */
public class MultiTreadHttpDownloader2 {

    private Logger logger = LoggerFactory.getLogger(MultiTreadHttpDownloader2.class);

    /**
     * 定义文件名称
     */
    private String filename;
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
     * 默认存放路径
     */
    private static final String defaultTargetPath = "D:/videos";

    /**
     * 构造器
     *
     * @param path
     * @param targetFile
     * @param threadNum
     */
    public MultiTreadHttpDownloader2(String path, String targetFile, int threadNum) {
        this.path = path;
        this.threadNum = threadNum;
        this.threads = new DownThread[threadNum];// 初始化threads数组
        this.filename = path.substring(path.lastIndexOf("/") + 1);
        this.targetFile = StringUtils.isBlank(targetFile) ? defaultTargetPath + "/" + filename : targetFile + "/" + filename;

    }

    /**
     * 下载
     *
     * @throws Exception
     */
    public void download() throws Exception {
        URL url = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(30000);
        connection.connect();
        if (connection.getResponseCode() != 200) {// 200:请求资源成功
            System.out.println("资源" + filename + "请求失败, 错误码：" + connection.getResponseCode());
            return;
        }

        // 得到文件大小
        fileSize = connection.getContentLength();
        connection.disconnect();
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
        return (double) sumSize / fileSize; // 返回已经完成的百分比
    }

    /**
     * 获取当前下载文件名
     *
     * @return
     */
    public String getFilename() {
        return filename;
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
            InputStream inputStream = null;
            try {
                URL url = new URL(path);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(30000);
                connection.setReadTimeout(30000);
                connection.connect();
                inputStream = connection.getInputStream();
                inputStream.skip(this.startPos);// 跳过startPos个字节，表明该线程只下载自己负责哪部分文件。
                byte[] buffer = new byte[1024];
                int hasRead = 0;

                // 读取网络数据，并写入本地文件
                while (length < currentPartSize && (hasRead = inputStream.read(buffer)) != -1) {
                    currentPart.write(buffer, 0, hasRead);
                    length += hasRead;// 累计该线程下载的总大小
                }
            } catch (Exception e) {
                System.out.println(getFilename() + " 下载出错：" + e + " 请重新下载：" + path);
                e.printStackTrace();
            } finally {
                try {
                    if (currentPart != null) {
                        currentPart.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
