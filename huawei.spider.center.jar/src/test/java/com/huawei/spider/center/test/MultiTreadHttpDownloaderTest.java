package com.huawei.spider.center.test;

import com.huawei.spider.center.downloader.http.MultiTreadHttpDownloader;
import com.huawei.spider.center.downloader.http.MultiTreadHttpDownloader2;
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
        String targitFile = "D:/videos";
        String url = "https://201806.53didi.com/20180818/1/1/xml/91_647fb197eece4d0fe698561d730a27ed.mp4";
        int threadNum = 3;
        final MultiTreadHttpDownloader2 downloader = new MultiTreadHttpDownloader2(url, targitFile, threadNum);
        downloader.download();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (downloader.getCompleteRate() < 1) {
                    try {
                        System.out.println(downloader.getFilename() + "   已完成:" + downloader.getCompleteRate());
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
