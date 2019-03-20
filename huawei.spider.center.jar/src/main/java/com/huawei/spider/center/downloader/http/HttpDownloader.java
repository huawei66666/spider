package com.huawei.spider.center.downloader.http;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

/**
 * 功能：http、https协议 下载器
 * 作者：laihuawei(laihuawei@lianj.com)
 * 日期：2018年09月2018/9/7日 15:38
 * 版权所有：广东联结网络技术有限公司 版权所有(C)
 */
public class HttpDownloader {

    private Logger logger = LoggerFactory.getLogger(HttpDownloader.class);

    private static String defaultOutputPath = "D:/videos";// 默认存放路径

    /**
     * 下载文件到本地
     *
     * @param url        文件地址
     * @param outputPath 存放路径
     * @param timeout    超时时间毫秒
     * @throws Exception 各种异常
     */
    public void download(String url, String outputPath, int currentThread, int timeout) {
        FileOutputStream outputStream = null;
        BufferedInputStream inputStream = null;
        if (StringUtils.isBlank(url) || url.indexOf(".") == -1) {
            System.out.println(url + " 无效的地址");
            return;
        }
        if (StringUtils.isBlank(outputPath)) {// 若输出地址为空，拿默认存放路径
            outputPath = this.defaultOutputPath;
        }
        String filename = url.substring(url.lastIndexOf("/") + 1);
        outputPath = outputPath + "/" + filename;
        File file = new File(outputPath);
        try {

            URL u = new URL(url);// 构造URL
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();// 打开连接
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.connect();
            if (connection.getResponseCode() != 200) {// 200:请求资源成功
                System.out.println(url + " 资源请求失败, 错误码：" + connection.getResponseCode());
                return;
            }
            long contentLength = connection.getContentLengthLong();
            if (contentLength == 0 || contentLength == -1) {
                System.out.println(url + " 文件无效或不存在");
                return;
            }

            DecimalFormat df = new DecimalFormat("0.00");
            String size = df.format((double) contentLength / (1024 * 1024));
//            System.out.println("目标文件总大小（bytes）： " + contentLength + "B");
            System.out.println("目标文件总大小（MB）： " + size + "MB");

            File parent = new File(file.getParent());// 输出的文件流
            parent.mkdirs();

            if (file.exists()) {
                file.delete();
            }
            if (!file.isDirectory()) {
                file.createNewFile();//创建文件
            }

            outputStream = new FileOutputStream(file);
            inputStream = new BufferedInputStream(connection.getInputStream());// 输入流

            int total = 0;// 已下载量百分比
            double p = 0;// 上次下载量百分比
            int last = 0;// 上次下载量
            long preTime = System.currentTimeMillis();// 上次下载时间
            byte[] buffer = new byte[1024];
            int length = inputStream.read(buffer);
            while (length != -1) {
                outputStream.write(buffer, 0, length);
                double temp = Double.parseDouble(df.format(((double) total / contentLength) * 100));
                long curentTime = System.currentTimeMillis();
                if (temp > p && curentTime - preTime >= 1000) {// 时间差大于等于1s才输出
                    String percent = df.format(temp) + "%";
                    String loaded = df.format((double) total / (1024 * 1024));
                    String remaind = df.format((double) (contentLength - total) / (1024 * 1024));
                    String process = "线程" + currentThread + "正在下载：" + filename + "   进度：" + percent + "   总大小：" + size + "MB   已下载：" + loaded + "MB" + "   剩余：" + remaind + "MB";

                    // 速度 = 下载量 / 时间差（耗时）
                    String unit = "";
                    long speed = ((total - last) / (curentTime - preTime)) * 1000;
                    if (speed < 1024) {
                        unit = "B/s";
                    } else if (speed >= 1024 && speed < (1024 * 1024)) {
                        speed = speed / 1024;
                        unit = "KB/s";
                    } else if (speed >= (1024 * 1024)) {
                        speed = speed / (1024 * 1024);
                        unit = "MB/s";
                    }
                    process += "  下载速度：" + speed + unit;
                    System.out.println(process);

                    preTime = curentTime;
                    last = total;
                    p = temp;
                }
                length = inputStream.read(buffer);
                total += length;
            }
            outputStream.flush();

            if (contentLength != file.length()) {
                System.out.println(filename + " 下载失败，文件无效，地址：" + url);
                file.delete();
                return;
            }
            System.out.println(filename + " 下载完成！下载后文件总大小：" + df.format((double) file.length() / (1024 * 1024)) + "MB");
        } catch (FileNotFoundException fe) {
            System.out.println(url + " 地址无效，文件不存在！");
            logger.error("VIDEO URL：" + url + " FILENAME:" + filename + " DOWNLOAD FAILED!! ");
            if (file.exists()) {
                file.delete();
            }
//            fe.printStackTrace();
        } catch (Exception e) {
            System.out.println("无法下载 " + filename + "   URL地址：" + url + "   原因：" + e.getMessage());
            logger.error("VIDEO URL：" + url + " FILENAME:" + filename + " DOWNLOAD FAILED!! ");
            if (file.exists()) {
                file.delete();
            }
//            e.printStackTrace();
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
                logger.error("流关闭异常！", e);
                e.printStackTrace();
//                System.out.println("java虚拟机非正常退出！");
//                System.exit(1);
            }
        }
    }


    /**
     * 下载文件到本地
     *
     * @param url
     * @param filename
     * @param outputPath
     * @param timeout
     */
    public void download(String url, String filename, String outputPath, int timeout) {
        FileOutputStream outputStream = null;
        BufferedInputStream inputStream = null;
        if (StringUtils.isBlank(url) || url.indexOf(".") == -1) {
            System.out.println("无效的地址");
            return;
        }
        if (StringUtils.isBlank(outputPath)) {// 若输出地址为空，拿默认存放路径
            outputPath = this.defaultOutputPath;
        }
        filename = StringUtils.isBlank(filename) ? url.substring(url.lastIndexOf("/") + 1) : filename;
        outputPath = outputPath + "/" + filename;
        File file = new File(outputPath);
        try {

            URL u = new URL(url);// 构造URL
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();// 打开连接
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.connect();
            if (connection.getResponseCode() != 200) {// 200:请求资源成功
                System.out.println("资源请求失败, 错误码：" + connection.getResponseCode());
                return;
            }
            long contentLength = connection.getContentLengthLong();
            if (contentLength == 0 || contentLength == -1) {
                System.out.println("文件无效或不存在");
                return;
            }

            DecimalFormat df = new DecimalFormat("0.00");
            String size = df.format((double) contentLength / (1024 * 1024));
//            System.out.println("目标文件总大小（bytes）： " + contentLength + "B");
            System.out.println("目标文件总大小（MB）： " + size + "MB");

            File parent = new File(file.getParent());// 输出的文件流
            parent.mkdirs();

            if (file.exists()) {
                file.delete();
            }
            if (!file.isDirectory()) {
                file.createNewFile();//创建文件
            }

            outputStream = new FileOutputStream(file);
            inputStream = new BufferedInputStream(connection.getInputStream());// 输入流

            int total = 0;// 已下载量百分比
            double p = 0;// 上次下载量百分比
            int last = 0;// 上次下载量
            long preTime = System.currentTimeMillis();// 上次下载时间
            byte[] buffer = new byte[1024];
            int length = inputStream.read(buffer);
            while (length != -1) {
                outputStream.write(buffer, 0, length);
                double temp = Double.parseDouble(df.format(((double) total / contentLength) * 100));
                long curentTime = System.currentTimeMillis();
                if (temp > p && curentTime - preTime >= 1000) {// 时间差大于等于1s才输出
                    String percent = df.format(temp) + "%";
                    String loaded = df.format((double) total / (1024 * 1024));
                    String remaind = df.format((double) (contentLength - total) / (1024 * 1024));
                    String process = "正在下载：" + filename + "   进度：" + percent + "   总大小：" + size + "MB   已下载：" + loaded + "MB" + "   剩余：" + remaind + "MB";

                    // 速度 = 下载量 / 时间差（耗时）
                    String unit = "";
                    long speed = ((total - last) / (curentTime - preTime)) * 1000;
                    if (speed < 1024) {
                        unit = "B/s";
                    } else if (speed >= 1024 && speed < (1024 * 1024)) {
                        speed = speed / 1024;
                        unit = "KB/s";
                    } else if (speed >= (1024 * 1024)) {
                        speed = speed / (1024 * 1024);
                        unit = "MB/s";
                    }
                    process += "  下载速度：" + speed + unit;
                    System.out.println(process);

                    preTime = curentTime;
                    last = total;
                    p = temp;
                }
                length = inputStream.read(buffer);
                total += length;
            }
            outputStream.flush();

            if (contentLength != file.length()) {
                System.out.println(filename + " 下载失败，文件无效");
                file.delete();
                return;
            }
            System.out.println(filename + " 下载完成！下载后文件总大小：" + df.format((double) file.length() / (1024 * 1024)) + "MB");
        } catch (FileNotFoundException fe) {
            System.out.println(filename + " 地址无效，文件不存在！");
            logger.error("VIDEO URL：" + url + " FILENAME:" + filename + " DOWNLOAD FAILED!! ");
            if (file.exists()) {
                file.delete();
            }
//            fe.printStackTrace();
        } catch (Exception e) {
            System.out.println("无法下载 " + filename + "   原因：" + e.getMessage());
            logger.error("VIDEO URL：" + url + " FILENAME:" + filename + " DOWNLOAD FAILED!! ");
            if (file.exists()) {
                file.delete();
            }
//            e.printStackTrace();
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
                logger.error("流关闭异常！", e);
                e.printStackTrace();
//                System.out.println("java虚拟机非正常退出！");
//                System.exit(1);
            }
        }
    }

}
