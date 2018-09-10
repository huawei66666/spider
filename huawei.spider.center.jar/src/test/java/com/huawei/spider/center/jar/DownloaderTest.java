package com.huawei.spider.center.jar;

import com.huawei.spider.center.downloader.Downloader;
import org.junit.Test;

/**
 * 功能：视频下载测试
 * 作者：laihuawei(laihuawei@lianj.com)
 * 日期：2018年09月2018/9/7日 15:36
 * 版权所有：广东联结网络技术有限公司 版权所有(C)
 */
public class DownloaderTest {

    @Test
    public void testDownload() throws Exception {
//        String url = "https://aliuwmp3.changba.com/userdata/video/4DA2C4951547C4539C33DC5901307461.mp4";
//        String url = "https://dns.whbosscar.com/20180826/3/1/xml/91_a96d6a991c6b42929ac52fbd8c91b5d1.mp4";
//        String url = "https://dns.whbosscar.com/20180818/1/1/xml/91_dfd3194527d54cb29607e713168e01e4.mp4";
//        String url = "https://dns.whbosscar.com/20180818/1/1/xml/91_be38cb925a9a441a92e34308e23204ec.mp4";

        String url = "http://dcdiban.com/pic/uploadimg/2018-8/5789.jpg";
        String outputPath = "D:/videos";
//        String outputPath = "/Users/laihuawei/downloads";
//        String outputPath = "/mydoc/videos";
        Downloader downloader = new Downloader();
        downloader.download(url, outputPath, 30000);
    }

}
