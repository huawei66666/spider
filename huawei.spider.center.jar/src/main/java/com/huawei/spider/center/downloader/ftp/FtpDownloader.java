package com.huawei.spider.center.downloader.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.www.protocol.ftp.FtpURLConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

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
        FTPClient ftpClient = new FTPClient();
        FTPClientConfig config = new FTPClientConfig();
        // for example config.setServerTimeZoneId("Pacific/Pitcairn")
        ftpClient.configure(config);
        boolean error = false;
        try {

            ftpClient.setConnectTimeout(15000);
            //ftpClient.enterLocalActiveMode();    //主动模式
//            ftpClient.enterLocalPassiveMode(); // 被动模式
            ftpClient.setControlEncoding("utf-8");

            System.out.println("connecting ftp server...");

            if (!ftpClient.isConnected()) {
                ftpClient.connect(host, port); //连接ftp服务器
                ftpClient.login(username, password); //登录ftp服务器
            }

            System.out.print(ftpClient.getReplyString());
            int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                ftpClient.disconnect();
                System.err.println("FTP server refused connection.");
                System.exit(1);
            }
            System.out.println("connecting ftp server successful!");

            // transfer files
            ftpClient.logout();
        } catch (Exception e) {
            error = true;
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                    // do nothing
                    ioe.printStackTrace();
                }
            }
            System.exit(error ? 1 : 0);
        }
    }

    public void connectFtp(String url) {
        try {
            System.out.println("connecting ftp server...");

            //url = new String(url.getBytes(), "UTF-8");
            //System.out.println(url);

            // System.setProperty("file.encoding", "GBK");
            URL u = new URL(url);
            System.out.println(u.getHost());
            System.out.println(u.getPort());
            System.out.println(u.getUserInfo());
            System.out.println(u.getAuthority());
            System.out.println(u.getPath());
            System.out.println(u.getFile());

            URLConnection c = u.openConnection();
            FtpURLConnection connection = (FtpURLConnection) c;
            System.out.println(connection.getContentLength());

//            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String line = null;
//            if ((line = reader.readLine()) != null) {
//                System.out.println("line======" + line);
//            }

            System.out.println("connecting ftp server successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 下载文件到本地
     *
     * @param url
     */
    public void downloadFile(String url) {
        // connect("yg45.dydytt.net", 7413, "ygdy8", "ygdy8");

        connectFtp(url);
    }


}
