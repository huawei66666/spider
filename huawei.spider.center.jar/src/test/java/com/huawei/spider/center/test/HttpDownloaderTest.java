package com.huawei.spider.center.test;

import com.huawei.spider.center.downloader.http.HttpDownloader;
import com.huawei.spider.center.downloader.http.NIOHttpDownloader;
import org.junit.Test;

/**
 * 功能：视频下载测试
 * 作者：laihuawei(laihuawei@lianj.com)
 * 日期：2018年09月2018/9/7日 15:36
 * 版权所有：广东联结网络技术有限公司 版权所有(C)
 */
public class HttpDownloaderTest {

    @Test
    public void testDownloader() throws Exception {
//        String url = "https://aliuwmp3.changba.com/userdata/video/4DA2C4951547C4539C33DC5901307461.mp4";
//        String url = "https://dns.whbosscar.com/20180826/3/1/xml/91_a96d6a991c6b42929ac52fbd8c91b5d1.mp4";
//        String url = "https://dns.whbosscar.com/20180818/1/1/xml/91_dfd3194527d54cb29607e713168e01e4.mp4";
//        String url = "https://dns.whbosscar.com/20180818/1/1/xml/91_be38cb925a9a441a92e34308e23204ec.mp4";

        String url = "http://tracker.trackerfix.com:80/announce/Silencer.2018.720p.BluRay.H264.AAC-RARBG.mp4";
//        String outputPath = "D:/videos";
//        String outputPath = "/Users/laihuawei/downloads";
        String outputPath = "/mydoc/videos";
        HttpDownloader httpDownloader = new HttpDownloader();
        httpDownloader.download(url, outputPath, 30000);
    }

    @Test
    public void testNIODownloader() throws Exception {
        String url = "https://aliuwmp3.changba.com/userdata/video/4DA2C4951547C4539C33DC5901307461.mp4";
        String outputPath = "D:/videos";
//        String outputPath = "/Users/laihuawei/downloads";
//        String outputPath = "/mydoc/videos";
        NIOHttpDownloader downloader = new NIOHttpDownloader();
        downloader.download(url, outputPath, 30000);
    }

}
