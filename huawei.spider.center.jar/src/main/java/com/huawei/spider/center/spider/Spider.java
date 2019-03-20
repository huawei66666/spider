package com.huawei.spider.center.spider;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.huawei.spider.center.beans.UrlInfoBo;
import com.huawei.spider.center.parsers.HappyParser;
import com.huawei.spider.center.parsers.HappyParserV2;
import com.huawei.spider.center.parsers.HappyParserV3;
import com.huawei.spider.center.thread.DownloadTask;
import com.huawei.spider.center.thread.MultiThreadDownloadTask;
import com.huawei.spider.center.utils.ThreadHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 功能：爬虫
 * 作者：laihuawei(laihuawei@lianj.com)
 * 日期：2018年09月2018/9/14日 14:34
 * 版权所有：广东联结网络技术有限公司 版权所有(C)
 */
public class Spider {

    public static void main(String[] args) {
//        testSpider();
//        testMultiTreadDownloader();
//        executeFixedRate();
        testSpider3();
    }

    /**
     * 下载猜我喜欢
     */
    public static void testSpider3() {
        try {
            String outputPath = "/mydoc/videos/a/b/new";

            HappyParserV3 parser = new HappyParserV3();
            String startUrl = "http://dcdiban.com/vidol/index4905.html?4905-0-0";
            List<UrlInfoBo> urls = parser.parseFavorite(null, 2);
            if (CollectionUtils.isNotEmpty(urls)) {
                ExecutorService pool = ThreadHelper.THREAD_POOL;
                for (int i = 0; i < urls.size(); i++) {
                    UrlInfoBo bo = urls.get(i);
                    pool.submit(new DownloadTask(i + 1, bo.getName(), bo.getUrl(), outputPath));
                }
                pool.shutdown();// 线程执行完后停止
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载最新电影
     */
    public static void testSpider2() {
        try {
            // 本地链接存放地址
            String localFilePath = "/mydoc/happy.txt";
//            String localFilePath = "D:/happy.txt";

            String outputPath = "/mydoc/videos/a/b/new";

            HappyParserV2 parser = new HappyParserV2(localFilePath);
            List<UrlInfoBo> urls = parser.parse();
            if (CollectionUtils.isNotEmpty(urls)) {
                ExecutorService pool = ThreadHelper.THREAD_POOL;
                for (int i = 0; i < urls.size(); i++) {
                    UrlInfoBo bo = urls.get(i);
                    pool.submit(new DownloadTask(i + 1, bo.getName(), bo.getUrl(), outputPath));
                }
                pool.shutdown();// 线程执行完后停止
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出当前文件已下载百分比
     */
    public static void getCompleteRate() {
        System.out.println(String.format("已下载"));
    }

    /**
     * 以固定周期1s频率执行任务
     */
    public static void executeFixedRate() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Future future = executor.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        getCompleteRate();
                    }
                },
                0,
                1,
                TimeUnit.SECONDS);
    }

    public static void testSpider() {
        try {
            // 本地链接存放地址
            // String localPath = "/mydoc/happy.txt";
            String localFilePath = "D:/happy.txt";
            HappyParser parser = new HappyParser(localFilePath);
//        List<UrlInfoBo> urls = parser.parse();

            List<UrlInfoBo> urls = new ArrayList<>();
            UrlInfoBo urlInfoBo = new UrlInfoBo();
            urlInfoBo.setUrl("https://201806.53didi.com/20180818/1/1/xml/91_c1cb0fb10154489f929c22c181730f77.mp4");
            urlInfoBo.setName("测试文件1");
            urls.add(urlInfoBo);

            UrlInfoBo urlInfoBo2 = new UrlInfoBo();
            urlInfoBo2.setUrl("https://201806.53didi.com/20180818/1/1/xml/91_9730215b43e34f87c960f045241e8b62.mp4");
            urlInfoBo2.setName("测试文件2");
            urls.add(urlInfoBo2);

            UrlInfoBo urlInfoBo3 = new UrlInfoBo();
            urlInfoBo3.setUrl("https://201806.53didi.com/20180818/1/1/xml/91_647fb197eece4d0fe698561d730a27ed.mp4");
            urlInfoBo3.setName("测试文件3");
            urls.add(urlInfoBo3);

            UrlInfoBo urlInfoBo4 = new UrlInfoBo();
            urlInfoBo4.setUrl("https://dns.whbosscar.com/20180826/8/1/xml/91_2a6fdb0314ad41bcc060cd7785545abe.mp4");
            urlInfoBo4.setName("测试文件4");
            urls.add(urlInfoBo4);

            if (CollectionUtils.isNotEmpty(urls)) {
                ExecutorService pool = ThreadHelper.THREAD_POOL;
                for (int i = 0; i < urls.size(); i++) {
                    UrlInfoBo bo = urls.get(i);
                    pool.submit(new DownloadTask(i + 1, bo.getName(), bo.getUrl(), null));
                }
                pool.shutdown();// 线程执行完后停止
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testMultiTreadDownloader() {
        try {
            // 本地链接存放地址
            String localFilePath = "/mydoc/happy.txt";
//            String localFilePath = "D:/happy.txt";
            String outputPath = "/mydoc/videos";

            HappyParser parser = new HappyParser(localFilePath);
//            List<UrlInfoBo> urls = parser.parse();

            List<UrlInfoBo> urls = new ArrayList<>();
            UrlInfoBo urlInfoBo = new UrlInfoBo();
            urlInfoBo.setUrl("https://201806.53didi.com/20180818/1/1/xml/91_c1cb0fb10154489f929c22c181730f77.mp4");
            urlInfoBo.setName("测试文件1");
            urls.add(urlInfoBo);

            UrlInfoBo urlInfoBo2 = new UrlInfoBo();
            urlInfoBo2.setUrl("https://201806.53didi.com/20180818/1/1/xml/91_9730215b43e34f87c960f045241e8b62.mp4");
            urlInfoBo2.setName("测试文件2");
            urls.add(urlInfoBo2);

            UrlInfoBo urlInfoBo3 = new UrlInfoBo();
            urlInfoBo3.setUrl("https://201806.53didi.com/20180818/1/1/xml/91_647fb197eece4d0fe698561d730a27ed.mp4");
            urlInfoBo3.setName("测试文件3");
            urls.add(urlInfoBo3);

            UrlInfoBo urlInfoBo4 = new UrlInfoBo();
            urlInfoBo4.setUrl("https://dns.whbosscar.com/20180826/8/1/xml/91_2a6fdb0314ad41bcc060cd7785545abe.mp4");
            urlInfoBo4.setName("测试文件4");
            urls.add(urlInfoBo4);

            if (CollectionUtils.isNotEmpty(urls)) {
                ExecutorService pool = ThreadHelper.THREAD_POOL;
                for (int i = 0; i < urls.size(); i++) {
                    UrlInfoBo bo = urls.get(i);
                    pool.submit(new MultiThreadDownloadTask(i + 1, bo.getName(), bo.getUrl(), outputPath));
                }
                pool.shutdown();// 线程执行完后停止
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*public void testMultiTreadDownloader2() {
        try {
            // 本地链接存放地址
            // String localPath = "/mydoc/happy.txt";
            String localFilePath = "D:/happy.txt";
            HappyParser parser = new HappyParser(localFilePath);
//            List<UrlInfoBo> urls = parser.parse();

            List<UrlInfoBo> urls = new ArrayList<>();
            UrlInfoBo urlInfoBo = new UrlInfoBo();
            urlInfoBo.setUrl("https://201806.53didi.com/20180818/1/1/xml/91_c1cb0fb10154489f929c22c181730f77.mp4");
            urlInfoBo.setName("测试文件1");
            urls.add(urlInfoBo);

            UrlInfoBo urlInfoBo2 = new UrlInfoBo();
            urlInfoBo2.setUrl("https://201806.53didi.com/20180818/1/1/xml/91_9730215b43e34f87c960f045241e8b62.mp4");
            urlInfoBo2.setName("测试文件2");
            urls.add(urlInfoBo2);

            UrlInfoBo urlInfoBo3 = new UrlInfoBo();
            urlInfoBo3.setUrl("https://201806.53didi.com/20180818/1/1/xml/91_647fb197eece4d0fe698561d730a27ed.mp4");
            urlInfoBo3.setName("测试文件3");
            urls.add(urlInfoBo3);

            UrlInfoBo urlInfoBo4 = new UrlInfoBo();
            urlInfoBo4.setUrl("https://dns.whbosscar.com/20180826/8/1/xml/91_2a6fdb0314ad41bcc060cd7785545abe.mp4");
            urlInfoBo4.setName("测试文件4");
            urls.add(urlInfoBo4);

            if (CollectionUtils.isNotEmpty(urls)) {
                for (int i = 0; i < urls.size(); i++) {
                    UrlInfoBo bo = urls.get(i);

                    final MultiTreadHttpDownloader downloader = new MultiTreadHttpDownloader(bo.getUrl(), null, 3);
                    downloader.download();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (downloader.getCompleteRate() < 1) {
                                try {
                                    System.out.println(downloader.getFilename() + "  已完成:" + downloader.getCompleteRate());
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/
}
