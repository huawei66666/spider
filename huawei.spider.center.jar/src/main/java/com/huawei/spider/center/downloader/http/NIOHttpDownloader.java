package com.huawei.spider.center.downloader.http;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.DecimalFormat;

/**
 * 功能：多线程下载器
 * 作者：laihuawei(laihuawei@lianj.com)
 * 日期：2018年09月2018/9/7日 15:38
 * 版权所有：广东联结网络技术有限公司 版权所有(C)
 */
public class NIOHttpDownloader {

    private Logger logger = LoggerFactory.getLogger(NIOHttpDownloader.class);

    /**
     * 下载文件到本地
     *
     * @param url        文件地址
     * @param outputPath 存放路径
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
            if (connection.getResponseCode() != 200) {// 200:请求资源成功
                System.out.println("资源请求失败, 错误码：" + connection.getResponseCode());
                return;
            }
            int contentLength = connection.getContentLength();
            if (contentLength == 0 || contentLength == -1) {
                System.out.println("文件无效或不存在");
                return;
            }

            DecimalFormat df = new DecimalFormat("0.00");
            String size = df.format((double) contentLength / (1024 * 1024));
//            System.out.println("目标文件总大小（bytes）： " + contentLength + "B");
            System.out.println("目标文件总大小（MB）： " + size + "MB");

            inputStream = new BufferedInputStream(connection.getInputStream());// 输入流
            outputStream = new FileOutputStream(file);
            ReadableByteChannel readChannel = Channels.newChannel(u.openStream());
            outputStream.getChannel().transferFrom(readChannel, 0, Long.MAX_VALUE);
//            Files.copy(connection.getInputStream(), Paths.get(filename), StandardCopyOption.REPLACE_EXISTING);

            if (contentLength != file.length()) {
                System.out.println("下载失败，文件无效");
                return;
            }
            System.out.println("下载完成！下载后文件总大小：" + df.format((double) file.length() / (1024 * 1024)) + "MB");
        } catch (FileNotFoundException fe) {
            System.out.println("地址无效，文件不存在！");
            fe.printStackTrace();
            logger.error("[VideoUtil:download]:\n" + " VIDEO URL：" + url + " \n NEW FILENAME:" + filename + " DOWNLOAD FAILED!! ");
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
