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
        String url = "https://aliuwmp3.changba.com/userdata/video/4DA2C4951547C4539C33DC5901307461.mp4";
        VideoDownloader downloader = new VideoDownloader();

        String outputPath = "/mydoc/videos";
        downloader.download(url, outputPath, 20000);
    }

}
