package com.huawei.spider.center.jar;

import org.junit.Test;

/**
 * 功能：视频下载测试
 * 作者：laihuawei(laihuawei@lianj.com)
 * 日期：2018年09月2018/9/7日 15:36
 * 版权所有：广东联结网络技术有限公司 版权所有(C)
 */
public class VideoDownloaderTest {

    @Test
    public void testDownload() throws Exception {
//        String url = "https://aliuwmp3.changba.com/userdata/video/4DA2C4951547C4539C33DC5901307461.mp4";
        String outputPath = "/mydoc/videos";
//        String url = "https://dns.whbosscar.com/20180826/3/1/xml/91_a96d6a991c6b42929ac52fbd8c91b5d1.mp4";
//        String outputPath = "/Users/laihuawei/downloads";
        VideoDownloader downloader = new VideoDownloader();
        String url = "magnet:?xt=urn:btih:9447A5EA06C4B2D316DB35800005E636811D37B2";
        downloader.download(url, outputPath, 30000);
    }

}
