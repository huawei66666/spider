package com.huawei.spider.center.parsers;

import com.huawei.spider.center.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能：解析器
 * 日期：2018年06月2018/6/12日 14:51
 */
public class HttpClientParser {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientParser.class);

    /**
     * 初始化
     */
    private static void init() {
        String profile = "development";
        System.setProperty("spring.profiles.active", profile);
        new ClassPathXmlApplicationContext("huawei-spider-center-context.xml");
        logger.info(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()) + " HttpClientParser inited!");
    }

    public static void main(String[] args) {
//        init();
        getParser();
    }

    /**
     * get方式解析器
     */
    private static void getParser() {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet("http://www.dytt8.net/");
            httpGet.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            httpGet.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
            CloseableHttpResponse response = httpClient.execute(httpGet);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                System.out.println(statusCode);
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity);
                if (StringUtils.isNotEmpty(content)) {
                    content = new String(content.getBytes("ISO-8859-1"), "GBK");
                    FileUtils.writeToFile(content);
                    System.out.println(content);
                }
                EntityUtils.consume(entity);
            } else {
                logger.error("网站返回异常，状态码为：" + statusCode);
            }
        } catch (Exception e) {
            logger.error("解析异常", e);
            e.printStackTrace();
        }


    }

}
