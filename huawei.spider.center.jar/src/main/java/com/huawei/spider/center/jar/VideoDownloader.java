package com.huawei.spider.center.jar;

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
     * @param url      被下载的文件地址
     * @param filename 本地文件名
     * @param timeout  超时时间毫秒
     * @throws Exception 各种异常
     */
    public void download(String url, String filename, int timeout) {
        FileOutputStream outputStream = null;
        BufferedInputStream inputStream = null;
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
            int percent = 0;
            while (length != -1) {
                outputStream.write(buffer, 0, length);
                length = inputStream.read(buffer);
                percent += length;
                String process = percent < 1024 ? "已下载：" + percent + "KB" : "已下载：" + (percent / 1024) + "MB";
                System.out.println(process);
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
            // 完毕，关闭所有链接
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error("下载异常", e);
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("下载异常", e);
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 断点续传
     *
     * @param urlString
     * @param filename
     * @param timeout
     * @return
     */
    public boolean resumeDownload(String urlString, String filename, int timeout) throws Exception {
        boolean ret = false;
        File fileFinal = new File(filename);
        String tmpFileName = filename + ".tmp";
        File file = new File(tmpFileName);

        try {
            if (fileFinal.exists()) {
                ret = true;
            } else {
                long contentStart = 0;
                File file2 = new File(file.getParent());

                if (file.exists()) {
                    contentStart = file.length();
                } else {
                    file2.mkdirs();
                }
                // 构造URL
                URL url = new URL(urlString);
                // 打开连接
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(timeout);
                con.setReadTimeout(timeout);
                //设置续传的点
                if (contentStart > 0) {
                    con.setRequestProperty("RANGE", "bytes=" + contentStart + "-");
                }
                con.connect();
                int contentLength = con.getContentLength();
                // 输入流
                InputStream is = con.getInputStream();
                // 100Kb的数据缓冲
                byte[] bs = new byte[100 * 1024];
                // 读取到的数据长度
                int len;
                RandomAccessFile oSavedFile = new RandomAccessFile(tmpFileName, "rw");
                oSavedFile.seek(contentStart);
                // 开始读取
                while ((len = is.read(bs)) != -1) {
                    oSavedFile.write(bs, 0, len);
                }
                // 完毕，关闭所有链接
                oSavedFile.close();
                is.close();
                file.renameTo(fileFinal);
                ret = true;
            }
        } catch (IOException e) {
            file.delete();
            ret = false;
            logger.error("[VideoUtil:download]:\n" + " VIDEO URL：" + urlString + " \n NEW FILENAME:" + filename + " DOWNLOAD FAILED!! ");
            throw new Exception(e);
        } finally {
            return ret;
        }
    }
}
