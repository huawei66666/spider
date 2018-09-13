package com.huawei.spider.center.jar;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.huawei.spider.center.beans.UrlInfoBo;
import com.huawei.spider.center.parsers.HappyParser;
import com.huawei.spider.center.thread.DownloadTask;
import com.huawei.spider.center.utils.ThreadHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 功能：Spider
 * 日期：2018年06月2018/6/12日 10:51
 */
public class Spider {

    private static final Logger logger = LoggerFactory.getLogger(Spider.class);

    public static void main(String[] args) {
        try {

            // 本地链接存放地址
            // String localPath = "/mydoc/happy.txt";
            String localFilePath = "D:/happy.txt";
            HappyParser parser = new HappyParser(localFilePath);
//            List<UrlInfoBo> urls = parser.parse();

            List<UrlInfoBo> urls = new ArrayList<>();
            /*UrlInfoBo urlInfoBo = new UrlInfoBo();
            urlInfoBo.setUrl("https://201806.53didi.com/20180818/1/1/xml/91_c1cb0fb10154489f929c22c181730f77.mp4");
            urlInfoBo.setName("测试文件1");
            urls.add(urlInfoBo);

            UrlInfoBo urlInfoBo2 = new UrlInfoBo();
            urlInfoBo2.setUrl("https://201806.53didi.com/20180818/1/1/xml/91_9730215b43e34f87c960f045241e8b62.mp4");
            urlInfoBo2.setName("测试文件2");
            urls.add(urlInfoBo2);*/

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

                // 向学生传达“问题解答完毕后请举手示意！”
                pool.shutdown();

                // 向学生传达“XX分之内解答不完的问题全部带回去作为课后作业！”后老师等待学生答题
                // (所有的任务都结束的时候，返回TRUE)
                /*if (!pool.awaitTermination(awaitTime, TimeUnit.MILLISECONDS)) {
                    // 超时的时候向线程池中所有的线程发出中断(interrupted)。
                    pool.shutdownNow();
                }*/
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
