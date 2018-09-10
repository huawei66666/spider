package com.huawei.spider.center.jar;

import com.huawei.spider.center.parsers.HappyParser;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huawei on 2018/9/7.
 */
public class HappyParserTest {


    @Test
    public void test() throws Exception {
        HappyParser parser = new HappyParser();
        parser.parse();
    }

    @Test
    public void testJsoup() throws Exception {
        try {
            String domain = "http://dcdiban.com";
//            Document doc = Jsoup.connect(domain).get();

            Map<String, String> header = new HashMap<>();
            header.put("Accept", "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            header.put("Accept-Encoding", "gzip, deflate");
            header.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
            header.put("Cache-Control", "max-age=0");
            header.put("Connection", "keep-alive");
            header.put("Cookie", "__tins__19429611=%7B%22sid%22%3A%201536420285747%2C%20%22vd%22%3A%201%2C%20%22expires%22%3A%201536422085747%7D; __51cke__=; __tins__19432051=%7B%22sid%22%3A%201536420285751%2C%20%22vd%22%3A%201%2C%20%22expires%22%3A%201536422085751%7D; __tins__19485495=%7B%22sid%22%3A%201536420285796%2C%20%22vd%22%3A%201%2C%20%22expires%22%3A%201536422085796%7D; __51laig__=3");
            header.put("If-Modified-Since", "Mon, 27 Aug 2018 05:22:19 GMT");
            header.put("If-None-Match", "52bbc7ecc53dd41:e61");
            header.put("Upgrade-Insecure-Requests", "1");
            header.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
            Document doc = Jsoup
                    .connect(domain)
                    .headers(header)
                    .timeout(30000)
                    .get();

            Elements elements = doc.select(".caoporn_Maincontentfive .index_videoshow a");
            for (Element a : elements) {
                String name = a.select("p").html();
                System.out.println(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHappyParser() throws Exception {
        try {
            String domain = "http://dcdiban.com";
            Document doc = Jsoup.connect(domain).get();
            Elements elements = doc.select(".caoporn_Maincontentfive .index_videoshow a");
            for (Element a : elements) {
                String name = a.select("p").text();
                System.out.println(name);

                String src = a.select("img").attr("src");
                String img = domain + src;
                System.out.println(img);

                String html = a.attr("href");
                String htmlurl = domain + html;
                System.out.println(htmlurl);
//                    getHtmlContent(htmlurl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetHtmlContent() throws Exception {
        String url = "http://dcdiban.com/" + "view/index5880.html";
        if (StringUtils.isNoneBlank(url)) {
            Document doc = Jsoup.parse(new URL(url), 30000);
            if (doc != null) {
//                Elements a = doc.select("a[title:contains(\"第\")]");
                Elements a = doc.select(".videourl a");
                if (a != null && a.size() > 0) {
                    String videourl = "http://dcdiban.com" + a.attr("href");
                    Document video = Jsoup.parse(new URL(videourl), 30000);
                    if (video != null) {
//                        String u = "https://201806.53didi.com/20180826/16/1/xml/91_0632500b07744276b46f665e56a4e871.mp4";
//                        System.out.println(video.toString());
                        Elements scripts = video.select("script[src^='/playdata']");
                        String js = "http://dcdiban.com" + scripts.attr("src");
                        URL u = new URL(js);
                        InputStream ism = u.openStream();
                        BufferedInputStream bf = new BufferedInputStream(ism);
                        byte[] bytes = new byte[1024];
                        int len = bf.read(bytes);
                        StringBuffer sb = new StringBuffer();
                        while (len != -1) {
                            len = bf.read(bytes);
                            String str = new String(bytes, "utf-8");
                            sb.append(str.trim());
                        }
                        if (StringUtils.isNoneBlank(sb.toString())) {
                            String s = sb.toString();
                            s = s.substring(s.indexOf("https"), s.indexOf("$ckplayer"));
                            System.out.println(s);
                        }
                    } else {
                        System.out.println("没有获取到地址");
                    }
                }
            }
        }
    }

}
