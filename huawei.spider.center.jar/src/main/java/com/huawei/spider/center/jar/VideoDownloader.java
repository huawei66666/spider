package com.huawei.spider.center.jar;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

/**
 * 功能：视频下载器
 * 作者：laihuawei(laihuawei@lianj.com)
 * 日期：2018年09月2018/9/7日 15:38
 * 版权所有：广东联结网络技术有限公司 版权所有(C)
 */
public class VideoDownloader {

    private Logger logger = LoggerFactory.getLogger(VideoDownloader.class);

    /**
     * 下载文件到本地
     *
     * @param url        被下载的文件地址
     * @param outputPath 本地文件名
     * @param timeout    超时时间毫秒
     * @throws Exception 各种异常
     */
    public void download(String url, String outputPath, int timeout) {
        FileOutputStream outputStream = null;
        BufferedInputStream inputStream = null;
        if (StringUtils.isBlank(url) || url.indexOf(".") == -1) {
            System.out.println("无效的地址");
            return;
        }
        String filename = outputPath + url.substring(url.lastIndexOf("/"));
        try {
            File file = new File(filename);
            if (file.exists()) {
                file.delete();
            }
            if (!file.isDirectory()) {
                file.createNewFile();//创建文件
            }
            File parent = new File(file.getParent());// 输出的文件流
            parent.mkdirs();

            URL u = new URL(url);// 构造URL
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();// 打开连接
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.connect();
            int contentLength = connection.getContentLength();
            if (contentLength == -1) {
                System.out.println("无效的地址");
                return;
            }

            DecimalFormat df = new DecimalFormat("0.00");
            String size = df.format((double) contentLength / (1024 * 1024));
            System.out.println("目标文件总大小（bytes）： " + contentLength + "B");
            System.out.println("目标文件总大小（MB）： " + size + "MB");

            outputStream = new FileOutputStream(file);
            inputStream = new BufferedInputStream(connection.getInputStream());// 输入流

            byte[] buffer = new byte[1024];
            int length = inputStream.read(buffer);
            int total = 0;
            double p = 0;
            while (length != -1) {
                outputStream.write(buffer, 0, length);
                double temp = Double.parseDouble(df.format(((double) total / contentLength) * 100));
                if(temp > p){
                    String percent = df.format(temp) + "%";
                    String loaded = df.format((double) total / (1024 * 1024));
                    String remaind = df.format((double) (contentLength - total) / (1024 * 1024));
                    String process = "进度：" + percent + "   已下载：" + loaded + "MB" + "   剩余：" + remaind + "MB";
                    System.out.println(process);
                    p = temp;
                }
                length = inputStream.read(buffer);
                total += length;
            }
            outputStream.flush();

            if (contentLength != file.length()) {
                System.out.println("下载失败，文件无效");
            }
            System.out.println("下载后文件总大小：" + df.format((double) file.length() / (1024 * 1024)) + "MB");
        } catch (Exception e) {
            logger.error("[VideoUtil:download]:\n" + " VIDEO URL：" + url + " \n NEW FILENAME:" + filename + " DOWNLOAD FAILED!! ");
            e.printStackTrace();
        } finally {
            try {
                // 完毕，关闭所有链接
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.error("下载异常", e);
                e.printStackTrace();
            }
        }
    }

}
