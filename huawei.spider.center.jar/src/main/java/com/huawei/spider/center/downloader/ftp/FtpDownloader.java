package com.huawei.spider.center.downloader.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能：FTP 文件下载器
 * 作者：laihuawei(laihuawei@lianj.com)
 * 日期：2018年09月2018/9/12日 10:31
 * 版权所有：广东联结网络技术有限公司 版权所有(C)
 */
public class FtpDownloader {

    private Logger logger = LoggerFactory.getLogger(FtpDownloader.class);

    private static String defaultOutputPath = "/mydoc/videos";// 默认存放路径

    public static void main(String[] args) {
        String url = "ftp://ygdy8:ygdy8@yg45.dydytt.net:7413/阳光电影www.ygdy8.com.库尔斯克.BD.720p.英语中字.mkv";
        String url2 = "ftp://ygdy8:ygdy8@yg45.dydytt.net:8408/阳光电影www.ygdy8.com.三方国界.BD.720p.中英双字幕.mkv";

        FtpDownloader downloader = new FtpDownloader();
        downloader.downloadFile(url);
    }

    public void connect(String host, int port, String username, String password) {
        try {
            FTPClient ftpClient = new FTPClient();
            ftpClient.setConnectTimeout(15000);
            ftpClient.enterLocalActiveMode();    //主动模式
//            ftpClient.enterLocalPassiveMode(); // 被动模式
            ftpClient.setControlEncoding("utf-8");

            System.out.println("connecting ftp server...");
            ftpClient.connect(host, port); //连接ftp服务器
            ftpClient.login(username, password); //登录ftp服务器

            int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("connecting ftp server failed");
                return;
            }
            System.out.println("connecting ftp server successful!");
        } catch (Exception e) {
            e.printStackTrace();
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
    public void downloadFile(String url) {
        connect("yg45.dydytt.net", 8408, "ygdy8", "ygdy8");
    }


}
