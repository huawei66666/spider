package com.huawei.spider.center.jar;

import com.huawei.spider.center.downloader.http.MultiTreadHttpDownloader;
import org.junit.Test;

/**
 * 功能：多线程下载器测试类
 * 作者：laihuawei(laihuawei@lianj.com)
 * 日期：2018年09月2018/9/12日 10:06
 * 版权所有：广东联结网络技术有限公司 版权所有(C)
 */
public class MultiTreadHttpDownloaderTest {

    @Test
    public void testDownload() throws Exception {
//        String targitFile = "tomcat-7.0.54.zip";
//        String url = "http://mirrors.cnnic.cn/apache/tomcat/tomcat-7/v7.0.54/bin/apache-tomcat-7.0.54.zip";
        String targitFile = "D:/videos/aa.mp4";
        String url = "ftp://ygdy8:ygdy8@yg45.dydytt.net:8282/阳光电影www.ygdy8.com.摩天营救.HD.720p.中英双字幕.rmvb";
        int threadNum = 3;
        final MultiTreadHttpDownloader downloader = new MultiTreadHttpDownloader(url, targitFile, threadNum);
        downloader.download();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (downloader.getCompleteRate() < 1) {
                    System.out.println("已完成:" + downloader.getCompleteRate());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }
}
