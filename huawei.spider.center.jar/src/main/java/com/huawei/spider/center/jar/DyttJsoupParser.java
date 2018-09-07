package com.huawei.spider.center.jar;

import com.huawei.spider.center.utils.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 功能：电影天堂解析器
 * 作者：xiaowei
 * 日期：2018年06月2018/6/25日 16:21
 */
public class DyttJsoupParser {

    private static final Logger logger = LoggerFactory.getLogger(DyttJsoupParser.class);

    public static void main(String[] args) {
        parseHtml();
    }

    private static void parseHtml() {
        try {
            String url = "http://www.dytt8.net/";// 解析电影天堂
            Document doc = Jsoup.connect(url).get();
            logger.debug(doc.title());
            Elements newsHeadlines = doc.select(".co_area2 .co_content8").eq(0);

            // 2018新片精品15部
            String html = "2018新片精品15部" + "\n";
//            html = new String(html.getBytes("GBK"), "UTF-8") + "\n";
            String rootUrl = url.substring(0, url.lastIndexOf("/"));
            for (Element line : newsHeadlines) {
                Elements tds = line.getElementsByTag("td");
                for (Element td : tds) {
                    Elements a = td.select("a").eq(1);
                    String href = a.attr("href");
                    if (StringUtils.isNotEmpty(href)) {
                        String downloadURL = getLinks(rootUrl + href);
                        String content = a.text() + ":" + rootUrl + href;
                        if (StringUtils.isNotEmpty(downloadURL)) {
                            content += "     downloadURL: " + downloadURL;
                        }
                        System.out.println(content);
                        html += content + "\n";
                    }
                }
            }

            // 最新发布168部影视
            String title2 = "\n\n最新发布168部影视";
//            title2 = new String(title2.getBytes("GBK"), "UTF-8");
            html += title2 + "\n";

            Elements movie168 = doc.select(".co_area2 .co_content2").eq(1);
            Elements as = movie168.select("a");
            for (Element a : as) {
                String name = a.text();
                String href = a.attr("href");
                String downloadURL = getLinks(rootUrl + href);
                String print = name + ": " + rootUrl + href;
                if (StringUtils.isNotEmpty(downloadURL)) {
                    print += "      downloadURL: " + downloadURL;
                }
                System.out.println(print);
                html += print + "\n";
            }

            FileUtil.writeToFile(html);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 提取电影链接
     *
     * @return
     */
    public static String getLinks(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements a = doc.select("td[style=\"WORD-WRAP: break-word\"][bgcolor=\"#fdfddf\"] a");
            String downloadUrl = a.attr("href");
            System.out.println(downloadUrl);
            return downloadUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
